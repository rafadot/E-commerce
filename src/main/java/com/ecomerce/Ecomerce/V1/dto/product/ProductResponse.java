package com.ecomerce.Ecomerce.V1.dto.product;

import com.ecomerce.Ecomerce.V1.model.Sale;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private UUID id;
    private String name;
    private String brand;
    private String profile;
    private String color;
    private String priceReal;
    private String priceDollar;
    private String descriptionEn;
    private String descriptionBr;
    private Sale sale;
}
