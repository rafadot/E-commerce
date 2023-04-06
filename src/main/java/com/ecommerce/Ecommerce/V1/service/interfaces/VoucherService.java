package com.ecommerce.Ecommerce.V1.service.interfaces;

import com.ecommerce.Ecommerce.V1.dto.voucher.VoucherRequest;
import com.ecommerce.Ecommerce.V1.model.enums.VoucherType;

import java.math.BigDecimal;

public interface VoucherService {
    Object create(
            VoucherRequest voucherRequest,
            VoucherType type,
            Integer discountPercentage,
            BigDecimal discountValueDollar);
}
