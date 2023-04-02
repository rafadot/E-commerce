package com.ecomerce.Ecomerce.V1.service.interfaces;

import com.ecomerce.Ecomerce.V1.dto.product.ProductRequest;
import com.ecomerce.Ecomerce.V1.dto.product.ProductResponse;
import com.ecomerce.Ecomerce.V1.model.Brand;
import com.ecomerce.Ecomerce.V1.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface BrandService {
    Brand create(String name);
    ProductResponse addProduct(Brand brand, ProductRequest productRequest, MultipartFile file) throws IOException;
}
