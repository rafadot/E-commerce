package com.ecommerce.Ecommerce.V1.model;

import com.ecommerce.Ecommerce.V1.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String profile;
    private String color;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private BigDecimal priceReal;
    private BigDecimal priceDollar;
    private String descriptionEn;
    private String descriptionBr;

    @OneToOne(cascade = CascadeType.ALL)
    private Offer offer;

    @ManyToOne
    @JoinColumn(name = "brand_id", updatable = true, insertable = true)
    @JsonIgnore
    private Brand brand;
}
