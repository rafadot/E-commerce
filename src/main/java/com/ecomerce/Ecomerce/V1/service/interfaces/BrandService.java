package com.ecomerce.Ecomerce.V1.service.interfaces;

import com.ecomerce.Ecomerce.V1.model.Brand;
import com.ecomerce.Ecomerce.V1.model.Product;

import java.util.UUID;

public interface BrandService {
    Brand create(String name);
    void addProduct(Brand brand, Product product);
}
