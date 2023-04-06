package com.ecommerce.Ecommerce.V1.service.interfaces;

import com.ecommerce.Ecommerce.V1.dto.product.ProductRequest;
import com.ecommerce.Ecommerce.V1.dto.product.ProductResponse;
import com.ecommerce.Ecommerce.V1.model.Brand;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BrandService {
    Brand create(String name);
    ProductResponse addProduct(Brand brand, ProductRequest productRequest, MultipartFile file) throws IOException;
}
