package com.ecommerce.Ecommerce.V1.model;

import com.ecommerce.Ecommerce.V1.model.enums.VoucherType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    @Enumerated(EnumType.STRING)
    private VoucherType type;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateExpiration;
    private Integer discountPercentage;
    private BigDecimal discountValueDollar;
    private BigDecimal discountValueReal;
    private UUID creatorId;
}
