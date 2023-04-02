package com.ecomerce.Ecomerce.V1.service.impl;

import com.ecomerce.Ecomerce.V1.model.Brand;
import com.ecomerce.Ecomerce.V1.model.Product;
import com.ecomerce.Ecomerce.V1.repository.BrandRepository;
import com.ecomerce.Ecomerce.V1.repository.ProductRepository;
import com.ecomerce.Ecomerce.V1.service.interfaces.BrandService;
import com.ecomerce.Ecomerce.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    @Override
    public Brand create(String name) {
        Optional<Brand> optionalBrand = brandRepository.findByName(name);

        if(optionalBrand.isPresent())
            throw new BadRequestException("O nome que você escolheu para sua marca já existe!");

        Brand brand = new Brand(name);
        brandRepository.save(brand);

        return brand;
    }

    @Override
    public void addProduct(Brand brand, Product product) {
        for(Product p : brand.getProduct()){
            if(p.getName().equals(product.getName()))
                throw new BadRequestException("Este produto já existe!");
        }
        brand.getProduct().add(product);
        product.setBrand(brand);
        productRepository.save(product);
        brandRepository.save(brand);
    }
}
