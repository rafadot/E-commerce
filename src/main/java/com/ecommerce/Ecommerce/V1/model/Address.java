package com.ecommerce.Ecommerce.V1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;

    private String country;
    private String firstName;
    private String lastName;
    private String street;
    private String streetOptional;
    private String city;
    private String state;
    private String zipCode;
    private String phone;
}
