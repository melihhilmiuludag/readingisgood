package com.mhu.core.authentication.domain.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDto {

    @Size(max = 50)
    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String password;

}
