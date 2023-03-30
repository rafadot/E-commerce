package com.ecomerce.Ecomerce.V1.controller;

import com.ecomerce.Ecomerce.V1.dto.product.ProductRequest;
import com.ecomerce.Ecomerce.V1.dto.product.ProductResponse;
import com.ecomerce.Ecomerce.V1.model.Product;
import com.ecomerce.Ecomerce.V1.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @GetMapping
    public List<Product> findAll(){
        return productService.getAll();
    }
}
