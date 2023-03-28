package com.ecomerce.Ecomerce.V1.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    @NotNull
    private String fullName;
    @NotNull
    private String email;
    @NotNull
    private String password;
}
