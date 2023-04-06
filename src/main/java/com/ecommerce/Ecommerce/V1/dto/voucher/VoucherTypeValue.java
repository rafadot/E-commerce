package com.ecommerce.Ecommerce.V1.dto.voucher;

import com.ecommerce.Ecommerce.V1.model.enums.VoucherType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherTypeValue {
    private UUID id;
    private String name;
    private VoucherType type;
    private LocalDateTime dateExpiration;
    private String discountValueDollar;
    private String discountValueReal;
    private String creatorName;
}
