package com.ecommerce.Ecommerce.V1.controller;

import com.ecommerce.Ecommerce.V1.dto.product.ProductCartDto;
import com.ecommerce.Ecommerce.V1.dto.voucher.VoucherRequest;
import com.ecommerce.Ecommerce.V1.model.enums.VoucherType;
import com.ecommerce.Ecommerce.V1.service.interfaces.CartService;
import com.ecommerce.Ecommerce.V1.service.interfaces.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final VoucherService voucherService;
    private final CartService cartService;

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

    @GetMapping("/add-to-cart")
    public ResponseEntity<String> addToCart(@RequestParam UUID productId){
        return new ResponseEntity<>(cartService.addToCart(productId),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductCartDto>> getCart(){
        return new ResponseEntity<>(cartService.getCart(),HttpStatus.OK);
    }
}
