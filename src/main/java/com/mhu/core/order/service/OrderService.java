package com.mhu.core.order.service;


import com.mhu.core.authentication.utils.AuthUtil;
import com.mhu.core.book.domain.entity.Book;
import com.mhu.core.book.service.BookService;
import com.mhu.core.customer.domain.entity.Customer;
import com.mhu.core.order.domain.dto.OrderByCustomerPageDto;
import com.mhu.core.order.domain.dto.OrderDto;
import com.mhu.core.order.domain.dto.PlaceOrderRequestDto;
import com.mhu.core.order.domain.entity.Orders;
import com.mhu.core.order.repository.OrderRepository;
import com.mhu.core.sharing.validation.DateValidator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final BookService bookService;
    private final EntityManager entityManager;
    private final AuthUtil authUtil;

    @Transactional
    public OrderDto placeAnOrder(PlaceOrderRequestDto requestDto){
        Customer currentCustomer = authUtil.getCurrentCustomer();
        Long bookId = requestDto.getBookId();
        Book book = entityManager.find(Book.class, bookId, LockModeType.PESSIMISTIC_WRITE);
        if(book == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Book with id %d does not exist",bookId));
        }
        int currentStockQuantity = book.getQuantityInStock();
        if(currentStockQuantity < requestDto.getPiece()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("You ordered to buy %d but there is only %d left from the book you requested",
                            requestDto.getPiece(),
                            currentStockQuantity));
        }

        LocalDate purchaseDate = DateValidator.validate(requestDto.getPurchaseDate());
        if(purchaseDate == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Please use the format 'yyyy-MM-dd' for the date provided as it seems to be invalid.");
        }

        bookService.updateBookStockByEntity(book,currentStockQuantity - requestDto.getPiece());

        Orders order = Orders.builder()
                .bookId(bookId)
                .bookTitle(book.getTitle())
                .purchasedBookCount(requestDto.getPiece())
                .purchasedAmount(requestDto.getPiece() * book.getPrice())
                .purchaseDate(purchaseDate)
                .customerId(currentCustomer.getId())
                .build();
        orderRepository.save(order);
        return OrderDto.builder()
                .id(order.getId())
                .bookId(book.getId())
                .bookName(book.getTitle())
                .purchasedAmount(order.getPurchasedAmount())
                .purchasedBookCount(order.getPurchasedBookCount())
                .purchaseDate(order.getPurchaseDate())
                .customerId(order.getCustomerId())
                .build();
    }

    public List<OrderDto> getAllOrders(){
        return orderRepository.findAll().stream()
                .map(
                        order -> OrderDto.builder()
                                .id(order.getId())
                                .bookId(order.getBookId())
                                .bookName(order.getBookTitle())
                                .purchasedBookCount(order.getPurchasedBookCount())
                                .purchasedAmount(order.getPurchasedAmount())
                                .purchaseDate(order.getPurchaseDate())
                                .customerId(order.getCustomerId())
                                .build()
                ).toList();
    }

    public List<OrderDto> getAllOrdersBetween(String start, String end){
        LocalDate startDate = DateValidator.validate(start);
        LocalDate endDate = DateValidator.validate(end);
        if(startDate == null || endDate == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Please use the format 'yyyy-MM-dd' for the date provided as it seems to be invalid");
        }
        return orderRepository.findByPurchaseDateBetween(startDate,endDate)
                .stream()
                .map(
                        order -> OrderDto.builder()
                                .id(order.getId())
                                .bookId(order.getBookId())
                                .bookName(order.getBookTitle())
                                .purchasedBookCount(order.getPurchasedBookCount())
                                .purchasedAmount(order.getPurchasedAmount())
                                .purchaseDate(order.getPurchaseDate())
                                .customerId(order.getCustomerId())
                                .build()
                ).toList();
    }

    public OrderByCustomerPageDto getOrdersByCustomer(Long customerId, int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize,Sort.by("purchaseDate").descending());
        Page<Orders> orders = orderRepository.findByCustomerId(customerId,pageable);
        List<OrderDto> listOfOrders =
                orders.getContent().stream()
                    .map(
                            order -> OrderDto.builder()
                                    .id(order.getId())
                                    .bookId(order.getBookId())
                                    .bookName(order.getBookTitle())
                                    .purchasedBookCount(order.getPurchasedBookCount())
                                    .purchasedAmount(order.getPurchasedAmount())
                                    .purchaseDate(order.getPurchaseDate())
                                    .customerId(order.getCustomerId())
                                    .build()
                    ).toList();

        return OrderByCustomerPageDto.builder()
                .pageNo(orders.getNumber())
                .pageSize(orders.getSize())
                .numberOfElements(orders.getNumberOfElements())
                .totalPages(orders.getTotalPages())
                .orders(listOfOrders)
                .build();
    }

    public List<Orders> findByCustomerIdAndYear(Long userId, int year) {
        return orderRepository.findByCustomerIdAndYear(userId, year);
    }
}
