package com.mhu.core.authentication.service;

import com.mhu.core.authentication.domain.dto.AuthenticationRequestDto;
import com.mhu.core.authentication.domain.dto.AuthenticationResponseDto;
import com.mhu.core.authentication.domain.dto.RegisterRequestDto;
import com.mhu.core.authentication.domain.dto.RegisterResponseDto;
import com.mhu.core.authentication.jwt.JwtService;
import com.mhu.core.customer.domain.entity.Customer;
import com.mhu.core.customer.domain.enums.UserRole;
import com.mhu.core.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final CustomerRepository customerRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponseDto registerCustomer(RegisterRequestDto registerRequestDto){
        if(customerRepository.findByEmail(registerRequestDto.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Customer with '%s' email already exists", registerRequestDto.getEmail()));
        }

        Customer customer = Customer.builder()
                .username(registerRequestDto.getUsername())
                .email(registerRequestDto.getEmail())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .role(UserRole.DEFAULT_USER)
                .build();
        customerRepository.save(customer);

        return RegisterResponseDto.builder()
                .message(String.format(
                        "%s registered successfully, please login using your credentials to generate a token.",
                        registerRequestDto.getEmail()
                )).build();
    }

    public AuthenticationResponseDto loginCustomer(AuthenticationRequestDto loginRequestDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(loginRequestDto.getEmail());
            return AuthenticationResponseDto.builder()
                    .token(token)
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password entered");
        }
    }

}
