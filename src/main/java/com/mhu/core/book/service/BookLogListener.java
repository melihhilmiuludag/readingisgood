package com.mhu.core.book.service;

import com.mhu.core.authentication.utils.AuthUtil;
import com.mhu.core.book.domain.entity.Book;
import com.mhu.core.customer.domain.entity.Customer;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Slf4j
public class BookLogListener {

    @Autowired
    private AuthUtil authUtil;

    @PostPersist
    public void bookAdded(Book book) {
        Customer currentCustomer = authUtil.getCurrentCustomer();
        log.info(String.format("%s added a new book titled %s - %s",
                currentCustomer.getUsername(),
                book.getTitle(),
                LocalDateTime.now()));
    }

    @PostUpdate
    public void bookUpdated(Book book) {
        Customer currentCustomer = authUtil.getCurrentCustomer();
        log.info(String.format("%s changed %s's stock quantity to %d - %s",
                currentCustomer.getUsername(),
                book.getTitle(),
                book.getQuantityInStock(),
                LocalDateTime.now()));
    }
}
