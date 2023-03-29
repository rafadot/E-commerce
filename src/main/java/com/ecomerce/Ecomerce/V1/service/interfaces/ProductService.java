package com.ecomerce.Ecomerce.V1.service.interfaces;

import com.ecomerce.Ecomerce.V1.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    Product create (MultipartFile file) throws IOException;
}
