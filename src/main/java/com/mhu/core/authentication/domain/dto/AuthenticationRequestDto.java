package com.mhu.core.authentication.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequestDto {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
