package com.ecomerce.Ecomerce.V1.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
