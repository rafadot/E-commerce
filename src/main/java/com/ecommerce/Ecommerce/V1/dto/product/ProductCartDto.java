package com.ecommerce.Ecommerce.V1.dto.product;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCartDto {
    private UUID id;
    private String name;
    private String profile;
    private BigDecimal priceReal;
    private BigDecimal priceDollar;
    private int amount;
}
