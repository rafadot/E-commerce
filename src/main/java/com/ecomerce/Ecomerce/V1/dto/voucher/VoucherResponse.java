package com.ecomerce.Ecomerce.V1.dto.voucher;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherResponse {
    private UUID id;
    private String name;
    private LocalDateTime dateExpiration;
    private Integer discountPercentage;
    private String creatorName;
}
