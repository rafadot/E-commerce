package com.ecommerce.Ecommerce.V1.service.interfaces;

import com.ecommerce.Ecommerce.V1.dto.offer.OfferRequest;
import com.ecommerce.Ecommerce.V1.dto.offer.OfferResponse;
import com.ecommerce.Ecommerce.V1.dto.product.ProductRequest;
import com.ecommerce.Ecommerce.V1.dto.product.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductResponse create (ProductRequest request, MultipartFile file) throws IOException;
    List<ProductResponse> findByBrandName(String brandName);
    String deleteAll() throws IOException;
    OfferResponse createOffer(OfferRequest offerRequest);
}
