package com.ecommerce.Ecommerce.V1.service.interfaces;

import com.ecommerce.Ecommerce.V1.dto.product.ProductCartDto;
import com.ecommerce.Ecommerce.V1.model.Product;

import java.util.List;
import java.util.UUID;

public interface CartService {
    String addToCart(UUID productID);
    List<ProductCartDto> getCart();
}
