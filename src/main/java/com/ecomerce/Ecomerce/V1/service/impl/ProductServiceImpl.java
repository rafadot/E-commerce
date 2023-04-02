package com.ecomerce.Ecomerce.V1.service.impl;

import com.ecomerce.Ecomerce.V1.dto.product.ProductRequest;
import com.ecomerce.Ecomerce.V1.dto.product.ProductResponse;
import com.ecomerce.Ecomerce.V1.model.Account;
import com.ecomerce.Ecomerce.V1.model.Brand;
import com.ecomerce.Ecomerce.V1.model.Product;
import com.ecomerce.Ecomerce.V1.model.Sale;
import com.ecomerce.Ecomerce.V1.repository.BrandRepository;
import com.ecomerce.Ecomerce.V1.repository.ProductRepository;
import com.ecomerce.Ecomerce.V1.repository.SaleRepository;
import com.ecomerce.Ecomerce.V1.service.interfaces.AccountService;
import com.ecomerce.Ecomerce.V1.service.interfaces.BrandService;
import com.ecomerce.Ecomerce.V1.service.interfaces.ProductService;
import com.ecomerce.Ecomerce.V1.util.AccountUtil;
import com.ecomerce.Ecomerce.V1.util.CloudUtil;
import com.ecomerce.Ecomerce.V1.util.ConversionUtil;
import com.ecomerce.Ecomerce.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final AccountService accountService;
    private final BrandRepository brandRepository;
    private final BrandService brandService;

    @Override
    public ProductResponse create(ProductRequest request, MultipartFile file) throws IOException {
        Account account = accountService.accountContext();
        if(!AccountUtil.containRole(account.getRole(), "BRAND"))
            throw new BadRequestException("A criação de produtos é reservada apenas para marcas!");

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
        brandService.addProduct(account.getBrand(),product);

        return ProductResponse
                .builder()
                .id(product.getId())
                .name(product.getName())
                .brand(account.getBrand().getName())
                .profile(product.getProfile())
                .color(product.getColor())
                .priceReal("R$" + ConversionUtil.formatMoney(product.getPriceReal()))
                .priceDollar("$" + ConversionUtil.formatMoney(product.getPriceDollar()))
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
