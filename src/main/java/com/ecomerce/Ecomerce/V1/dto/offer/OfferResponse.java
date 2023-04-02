package com.ecomerce.Ecomerce.V1.dto.offer;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferResponse {
    private UUID id;
    private Boolean status;
    private String percentage;
    private LocalDateTime expiration;
    private String offerPriceReal;
    private String offerPriceDollar;
}
