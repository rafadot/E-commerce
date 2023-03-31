package com.ecomerce.Ecomerce.V1.dto.account;

import com.ecomerce.Ecomerce.V1.model.Role;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {
    private UUID id;
    private String fullName;
    private String email;
    private List<String> role;

    public void setRole(Set<Role> role) {
        this.role = role
                .stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}
