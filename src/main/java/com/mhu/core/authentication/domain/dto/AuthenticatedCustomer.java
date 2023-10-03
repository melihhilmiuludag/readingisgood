package com.mhu.core.authentication.domain.dto;


import com.mhu.core.customer.domain.entity.Customer;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AuthenticatedCustomer extends User {

    private final Customer customer;

    public AuthenticatedCustomer(String username, String password, Collection<? extends GrantedAuthority> authorities, Customer customer) {
        super(username, password, authorities);
        this.customer = customer;
    }
}
