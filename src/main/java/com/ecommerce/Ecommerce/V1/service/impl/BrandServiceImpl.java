package com.ecommerce.Ecommerce.V1.service.impl;

import com.ecommerce.Ecommerce.V1.dto.product.ProductRequest;
import com.ecommerce.Ecommerce.V1.dto.product.ProductResponse;
import com.ecommerce.Ecommerce.V1.model.Brand;
import com.ecommerce.Ecommerce.V1.model.Product;
import com.ecommerce.Ecommerce.V1.model.Offer;
import com.ecommerce.Ecommerce.V1.repository.BrandRepository;
import com.ecommerce.Ecommerce.V1.repository.ProductRepository;
import com.ecommerce.Ecommerce.V1.repository.OfferRepository;
import com.ecommerce.Ecommerce.V1.service.interfaces.BrandService;
import com.ecommerce.Ecommerce.V1.util.CloudUtil;
import com.ecommerce.Ecommerce.V1.util.ConversionUtil;
import com.ecommerce.Ecommerce.V1.util.OfferUtil;
import com.ecommerce.Ecommerce.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final OfferRepository saleRepository;

    @Override
    public Brand create(String name) {
        Optional<Brand> optionalBrand = brandRepository.findByNameIgnoreCase(name);

        if(optionalBrand.isPresent())
            throw new BadRequestException("O nome que você escolheu para sua marca já existe!");

        Brand brand = new Brand(name);
        brandRepository.save(brand);

        return brand;
    }

    @Override
    public ProductResponse addProduct(Brand brand, ProductRequest productRequest, MultipartFile file) throws IOException {
        for(Product p : brand.getProduct()){
            if(p.getName().equals(productRequest.getName()))
                throw new BadRequestException("Você já possui esse produto!");
        }

        if(productRepository.findByName(productRequest.getName()).isPresent())
            throw new BadRequestException("Outra marca já possui esse produto!");

        Offer offer = new Offer();
        offer.setStatus(false);
        saleRepository.save(offer);

        Product product = Product
                .builder()
                .name(productRequest.getName())
                .profile(file == null ? null : CloudUtil.uploadImage(file))
                .color(productRequest.getColor())
                .gender(productRequest.getGender())
                .priceDollar(productRequest.getPriceDollar())
                .priceReal(ConversionUtil.dollarToReal(productRequest.getPriceDollar()))
                .offer(offer)
                .descriptionBr(productRequest.getDescriptionBr())
                .descriptionEn(productRequest.getDescriptionEn())
                .build();

        brand.getProduct().add(product);
        product.setBrand(brand);
        productRepository.save(product);
        brandRepository.save(brand);

        return ProductResponse
                .builder()
                .id(product.getId())
                .name(product.getName())
                .color(product.getColor())
                .offer(OfferUtil.buildOfferResponse(product.getOffer()))
                .descriptionEn(product.getDescriptionEn())
                .descriptionBr(product.getDescriptionBr())
                .gender(product.getGender())
                .priceReal("R$" + ConversionUtil.formatMoney(product.getPriceReal()))
                .priceDollar("$" + ConversionUtil.formatMoney(product.getPriceDollar()))
                .profile(product.getProfile())
                .build();
    }
}
