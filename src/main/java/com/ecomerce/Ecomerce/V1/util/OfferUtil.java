package com.ecomerce.Ecomerce.V1.util;

import com.ecomerce.Ecomerce.V1.dto.offer.OfferResponse;
import com.ecomerce.Ecomerce.V1.model.Offer;

public class OfferUtil {
    public static OfferResponse buildOfferResponse(Offer offer){
        return offer.getStatus().equals(false) ?
                OfferResponse
                        .builder()
                        .id(offer.getId())
                        .status(offer.getStatus())
                        .expiration(null)
                        .offerPriceReal(null)
                        .offerPriceDollar(null)
                        .percentage(null)
                        .build()
                :
                OfferResponse
                        .builder()
                        .id(offer.getId())
                        .status(offer.getStatus())
                        .expiration(offer.getExpiration())
                        .offerPriceReal("R$" + ConversionUtil.formatMoney(offer.getOfferPriceReal()))
                        .offerPriceDollar("$" + ConversionUtil.formatMoney(offer.getOfferPriceDollar()))
                        .percentage(offer.getPercentage() + "% Off")
                        .build();
    }
}
