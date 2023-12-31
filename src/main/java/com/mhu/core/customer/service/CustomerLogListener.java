package com.mhu.core.customer.service;



import com.mhu.core.customer.domain.entity.Customer;

import jakarta.persistence.PostPersist;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;


@Slf4j
public class CustomerLogListener {


    @PostPersist
    public void customerRegistered(Customer customer){
        log.info(String.format("%s registered with username %s - %s",
                customer.getEmail(),
                customer.getUsername(),
                LocalDateTime.now()
                ));
    }
}
