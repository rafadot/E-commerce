package com.ecomerce.Ecomerce.V1.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Boolean status;
    private Integer percentage;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiration;
    private BigDecimal offerPriceReal;
    private BigDecimal offerPriceDollar;

    public Offer(boolean status) {
        this.status = status;
    }
}
