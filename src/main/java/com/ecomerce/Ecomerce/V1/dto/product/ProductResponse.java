package com.ecomerce.Ecomerce.V1.dto.product;

import com.ecomerce.Ecomerce.V1.dto.offer.OfferResponse;
import com.ecomerce.Ecomerce.V1.model.enums.Gender;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private UUID id;
    private String name;
    private String profile;
    private String color;
    private Gender gender;
    private String priceReal;
    private String priceDollar;
    private String descriptionEn;
    private String descriptionBr;
    private OfferResponse offer;
}
