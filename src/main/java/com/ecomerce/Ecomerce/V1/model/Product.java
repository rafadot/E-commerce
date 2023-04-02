package com.ecomerce.Ecomerce.V1.model;

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
    private BigDecimal priceReal;
    private BigDecimal priceDollar;
    private String descriptionEn;
    private String descriptionBr;
    @OneToOne(cascade = CascadeType.ALL)
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "brand_id", updatable = true, insertable = true)
    @JsonIgnore
    private Brand brand;
}
