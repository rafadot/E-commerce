package com.ecomerce.Ecomerce.V1.service.impl;

import com.ecomerce.Ecomerce.V1.dto.product.ProductRequest;
import com.ecomerce.Ecomerce.V1.dto.product.ProductResponse;
import com.ecomerce.Ecomerce.V1.model.Product;
import com.ecomerce.Ecomerce.V1.model.Sale;
import com.ecomerce.Ecomerce.V1.repository.ProductRepository;
import com.ecomerce.Ecomerce.V1.repository.SaleRepository;
import com.ecomerce.Ecomerce.V1.service.interfaces.AccountService;
import com.ecomerce.Ecomerce.V1.service.interfaces.ProductService;
import com.ecomerce.Ecomerce.V1.util.CloudUtil;
import com.ecomerce.Ecomerce.V1.util.ConversionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.BindException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final AccountService accountService;

    @Override
    public ProductResponse create(ProductRequest request, MultipartFile file) throws IOException {
        if(!accountService.accountContext().getRole().toString().equals("BRAND") && !accountService.accountContext().getRole().toString().equals("ADMIN")){
            throw new BindException("A criação de produtos é reservada apenas para MARCAS ou ADMs!");
        }

        Product product = Product
                .builder()
                .name(request.getName())
                .profile(file != null ? CloudUtil.uploadImage(file) : null)
                .color(request.getColor())
                .priceReal(ConversionUtil.dollarToReal(request.getPriceDollar()))
                .priceDollar(request.getPriceDollar())
                .descriptionEn(request.getDescriptionEn())
                .descriptionBr(request.getDescriptionBr())
                .sale(saleRepository.save(new Sale(false)))
                .build();

        productRepository.save(product);

        return ProductResponse
                .builder()
                .id(product.getId())
                .name(product.getName())
                .profile(product.getProfile())
                .color(product.getColor())
                .priceReal(ConversionUtil.formatMoney(product.getPriceReal()))
                .priceDollar(ConversionUtil.formatMoney(product.getPriceDollar()))
                .descriptionEn(product.getDescriptionEn())
                .descriptionBr(product.getDescriptionBr())
                .sale(product.getSale())
                .build();
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
