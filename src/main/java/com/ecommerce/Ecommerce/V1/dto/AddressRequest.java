package com.ecommerce.Ecommerce.V1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    @NotNull
    private String country;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String street;
    private String streetOptional;
    @NotNull
    private String city;
    @NotNull
    private String state;
    @NotNull
    private String zipCode;
    @NotNull
    private String phone;
}
