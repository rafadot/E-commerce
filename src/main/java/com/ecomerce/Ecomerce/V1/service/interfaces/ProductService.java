package com.ecomerce.Ecomerce.V1.service.interfaces;

import com.ecomerce.Ecomerce.V1.dto.product.ProductRequest;
import com.ecomerce.Ecomerce.V1.dto.product.ProductResponse;
import com.ecomerce.Ecomerce.V1.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductResponse create (ProductRequest request, MultipartFile file) throws IOException;
    List<Product> getAll();
}
