package com.mhu.core.order.service;


import com.mhu.core.authentication.utils.AuthUtil;
import com.mhu.core.customer.domain.entity.Customer;
import com.mhu.core.order.domain.entity.Orders;
import jakarta.persistence.PostPersist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Slf4j
public class OrderLogListener {

    @Autowired
    private AuthUtil authUtil;

    @PostPersist
    public void orderPlaced(Orders order) {
        Customer currentCustomer = authUtil.getCurrentCustomer();
        log.info(String.format("%s placed an order for %d book(s) and paid %.2f$ in total - %s",
                currentCustomer.getUsername(),
                order.getPurchasedBookCount(),
                order.getPurchasedAmount(),
                LocalDateTime.now()));
    }
}
