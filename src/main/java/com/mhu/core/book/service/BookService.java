package com.mhu.core.book.service;


import com.mhu.core.book.domain.dto.BookDto;
import com.mhu.core.book.domain.dto.CreateBookRequestDto;
import com.mhu.core.book.domain.dto.UpdateBookStockRequestDto;
import com.mhu.core.book.domain.entity.Book;
import com.mhu.core.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public BookDto addBook(CreateBookRequestDto requestDto){
        Book book = Book.builder()
                .title(requestDto.getTitle())
                .author(requestDto.getAuthor())
                .price(requestDto.getPrice())
                .quantityInStock(requestDto.getQuantityInStock())
                .totalPages(requestDto.getTotalPages())
                .build();
        bookRepository.save(book);
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .quantityInStock(book.getQuantityInStock())
                .totalPages(book.getTotalPages())
                .build();
    }

    public BookDto updateBookStockById(UpdateBookStockRequestDto requestDto, Long bookId){
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("The book with id %d does not exist", bookId)));

        if (requestDto.getUpdatedStock() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Stock quantity cannot be lower than 0");
        }

        book.setQuantityInStock(requestDto.getUpdatedStock());
        bookRepository.save(book);

        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .quantityInStock(book.getQuantityInStock())
                .totalPages(book.getTotalPages())
                .build();

    }

    @Transactional
    public void updateBookStockByEntity(Book book, int updateStock)
    {
        book.setQuantityInStock(updateStock);
        bookRepository.save(book);
    }
}
