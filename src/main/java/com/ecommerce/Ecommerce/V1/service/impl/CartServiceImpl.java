package com.ecommerce.Ecommerce.V1.service.impl;

import com.ecommerce.Ecommerce.V1.dto.product.ProductCartDto;
import com.ecommerce.Ecommerce.V1.model.Account;
import com.ecommerce.Ecommerce.V1.model.Product;
import com.ecommerce.Ecommerce.V1.model.ProductsCart;
import com.ecommerce.Ecommerce.V1.repository.ProductRepository;
import com.ecommerce.Ecommerce.V1.repository.ProductsCartRepository;
import com.ecommerce.Ecommerce.V1.service.interfaces.AccountService;
import com.ecommerce.Ecommerce.V1.service.interfaces.CartService;
import com.ecommerce.Ecommerce.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final AccountService accountService;
    private final ProductsCartRepository productsCartRepository;
    private final ProductRepository productRepository;

    @Override
    public String addToCart(UUID productID) {
        Account account = accountService.accountContext();

        List<ProductsCart> cartList = productsCartRepository.findByAccountId(account.getId());

        if(cartList != null){
            for(ProductsCart pc : cartList){
                if(pc.getProductId().equals(productID)){
                    pc.setAmount(pc.getAmount() + 1);
                    productsCartRepository.save(pc);
                    return "Produto adicionado ao carrinho";
                }
            }
        }

        ProductsCart productsCart = new ProductsCart();
        productsCart.setProductId(productID);
        productsCart.setAccountId(account.getId());
        productsCart.setAmount(1);
        productsCartRepository.save(productsCart);

        return "Produto adicionado ao carrinho";
    }

    @Override
    public List<ProductCartDto> getCart() {
        Account account = accountService.accountContext();

        List<ProductsCart> cartList = productsCartRepository.findByAccountId(account.getId());

        if(cartList == null)
            return null;

        return cartList
                .stream()
                .map(m-> {
                    Product product = productRepository.findById(m.getProductId())
                            .orElseThrow(() -> new BadRequestException("Id do produto inválido, algo deu errado!"));

                    return ProductCartDto
                            .builder()
                            .id(product.getId())
                            .name(product.getName())
                            .profile(product.getProfile())
                            .priceDollar(product.getPriceDollar())
                            .priceReal(product.getPriceReal())
                            .amount(m.getAmount())
                            .build();
                })
                .collect(Collectors.toList());
    }
}
