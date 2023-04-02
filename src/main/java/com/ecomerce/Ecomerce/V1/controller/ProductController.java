package com.ecomerce.Ecomerce.V1.controller;

import com.ecomerce.Ecomerce.V1.dto.offer.OfferRequest;
import com.ecomerce.Ecomerce.V1.dto.offer.OfferResponse;
import com.ecomerce.Ecomerce.V1.dto.product.ProductRequest;
import com.ecomerce.Ecomerce.V1.dto.product.ProductResponse;
import com.ecomerce.Ecomerce.V1.model.Product;
import com.ecomerce.Ecomerce.V1.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "/create")
    public ResponseEntity<ProductResponse> create(@ModelAttribute @Valid ProductRequest product,
                                                  MultipartFile file) throws IOException {
        return new ResponseEntity<>(productService.create(product,file),HttpStatus.CREATED);
    }

    @GetMapping("/find-brand")
    public ResponseEntity<List<ProductResponse>> findByBrandName(@RequestParam String brandName){
        return new ResponseEntity<>(productService.findByBrandName(brandName),HttpStatus.OK);
    }

    @DeleteMapping("/delete-all-products")
    public ResponseEntity<String> deleteAllProducts(){
        return new ResponseEntity<>(productService.deleteAllProducts(),HttpStatus.OK);
    }

    @PostMapping("/create-offer")
    public ResponseEntity<OfferResponse> createOffer(@RequestBody OfferRequest offerRequest){
        return new ResponseEntity<>(productService.createOffer(offerRequest),HttpStatus.CREATED);
    }
}
