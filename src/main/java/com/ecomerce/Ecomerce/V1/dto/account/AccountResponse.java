package com.ecomerce.Ecomerce.V1.dto.account;

import com.ecomerce.Ecomerce.V1.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private UUID id;
    private String fullName;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
}
