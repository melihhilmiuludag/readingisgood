package com.mhu.core.authentication.utils;


import com.mhu.core.authentication.domain.dto.AuthenticatedCustomer;
import com.mhu.core.customer.domain.entity.Customer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    private Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Customer getCurrentCustomer(){
        Object principal = getAuthentication().getPrincipal();
        assert principal instanceof AuthenticatedCustomer;
        return ((AuthenticatedCustomer) principal).getCustomer();
    }
}
