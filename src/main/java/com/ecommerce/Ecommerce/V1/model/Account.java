package com.ecommerce.Ecommerce.V1.model;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String fullName;
    private String email;
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> role = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private PasswordRecovery passwordRecovery;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    private Brand brand;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @Valid
    private List<Address> addresses = new ArrayList<>();
}
