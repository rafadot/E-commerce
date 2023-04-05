package com.ecomerce.Ecomerce.V1.service.interfaces;

import com.ecomerce.Ecomerce.V1.dto.voucher.VoucherRequest;
import com.ecomerce.Ecomerce.V1.dto.voucher.VoucherResponse;
import com.ecomerce.Ecomerce.V1.model.Voucher;

public interface VoucherService {
    VoucherResponse create(VoucherRequest voucherRequest);
}
