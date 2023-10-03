package com.mhu.core.authentication.api;


import com.mhu.core.authentication.domain.dto.AuthenticationRequestDto;
import com.mhu.core.authentication.domain.dto.AuthenticationResponseDto;
import com.mhu.core.authentication.domain.dto.RegisterRequestDto;
import com.mhu.core.authentication.domain.dto.RegisterResponseDto;
import com.mhu.core.authentication.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(path = "/register")
    @Operation(tags = "Authentication Service")
    public ResponseEntity<RegisterResponseDto> registerCustomer(@Validated @RequestBody RegisterRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authenticationService.registerCustomer(requestDto));
    }

    @PostMapping(path = "/login")
    @Operation(tags = "Authentication Service")
    public ResponseEntity<AuthenticationResponseDto> loginCustomer(@Validated @RequestBody AuthenticationRequestDto requestDto){
        return ResponseEntity.ok(authenticationService.loginCustomer(requestDto));
    }
}
