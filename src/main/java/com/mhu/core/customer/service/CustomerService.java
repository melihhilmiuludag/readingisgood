package com.mhu.core.customer.service;

import com.mhu.core.authentication.utils.AuthUtil;
import com.mhu.core.customer.domain.dto.CustomerOrdersStatisticsDto;
import com.mhu.core.customer.domain.entity.Customer;
import com.mhu.core.order.domain.dto.OrderByCustomerPageDto;
import com.mhu.core.order.domain.entity.Orders;
import com.mhu.core.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final AuthUtil authUtil;
    private final OrderService orderService;

    public OrderByCustomerPageDto getCustomerOrders(int pageNo, int pageSize) {
        Customer currentCustomer = authUtil.getCurrentCustomer();
        return orderService.getOrdersByCustomer(currentCustomer.getId(), pageNo, pageSize);

    }

    public List<CustomerOrdersStatisticsDto> customerOrdersStatics(int year) {

        Customer currentCustomer = authUtil.getCurrentCustomer();
        List<Orders> bookOrders = orderService.findByCustomerIdAndYear(currentCustomer.getId(), year);

        Map<Integer, CustomerOrdersStatisticsDto> statisticsMap = new TreeMap<>(Collections.reverseOrder());
        for (Orders order : bookOrders) {
            int monthIndex = order.getPurchaseDate().getMonthValue();

            CustomerOrdersStatisticsDto statistics = statisticsMap.computeIfAbsent(monthIndex,
                    k -> CustomerOrdersStatisticsDto.builder()
                            .month(Month.of(monthIndex).toString())
                            .totalOrderCount(0)
                            .totalBookCount(0)
                            .totalPurchasedAmount(0)
                            .build());

            statistics.setTotalOrderCount(statistics.getTotalOrderCount() + 1);
            statistics.setTotalBookCount(statistics.getTotalBookCount() + order.getPurchasedBookCount());
            statistics.setTotalPurchasedAmount(statistics.getTotalPurchasedAmount() + order.getPurchasedAmount());
        }
        return new ArrayList<>(statisticsMap.values());
    }

}
