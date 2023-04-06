package com.ecommerce.Ecommerce.V1.controller;

import com.ecommerce.Ecommerce.V1.dto.voucher.VoucherRequest;
import com.ecommerce.Ecommerce.V1.model.enums.VoucherType;
import com.ecommerce.Ecommerce.V1.service.interfaces.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final VoucherService voucherService;

    @PostMapping("/create-voucher")
    public ResponseEntity<Object> createVoucher(
            @RequestBody @Valid VoucherRequest voucherRequest,
            @RequestParam VoucherType type,
            Integer discountPercentage,
            BigDecimal discountValueDollar
    ){
        return new ResponseEntity<>(voucherService.create(
                voucherRequest,
                type,
                discountPercentage,
                discountValueDollar
        ), HttpStatus.CREATED);
    }
}
