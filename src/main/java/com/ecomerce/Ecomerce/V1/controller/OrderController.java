package com.ecomerce.Ecomerce.V1.controller;

import com.ecomerce.Ecomerce.V1.dto.voucher.VoucherRequest;
import com.ecomerce.Ecomerce.V1.dto.voucher.VoucherResponse;
import com.ecomerce.Ecomerce.V1.model.Voucher;
import com.ecomerce.Ecomerce.V1.service.interfaces.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final VoucherService voucherService;

    @PostMapping("/create-voucher")
    public ResponseEntity<VoucherResponse> createVoucher(@RequestBody @Valid VoucherRequest voucherRequest){
        return new ResponseEntity<>(voucherService.create(voucherRequest), HttpStatus.CREATED);
    }
}
