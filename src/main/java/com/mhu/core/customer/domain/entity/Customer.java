package com.mhu.core.customer.domain.entity;


import com.mhu.core.customer.domain.enums.UserRole;
import com.mhu.core.customer.service.CustomerLogListener;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(CustomerLogListener.class)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

}
