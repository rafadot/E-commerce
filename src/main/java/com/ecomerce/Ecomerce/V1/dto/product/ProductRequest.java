package com.ecomerce.Ecomerce.V1.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotNull
    private String name;
    @NotNull
    private String color;
    @NotNull
    private BigDecimal priceDollar;
    @NotNull
    private String descriptionEn;
    @NotNull
    private String descriptionBr;
}
