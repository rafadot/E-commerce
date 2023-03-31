package com.ecomerce.Ecomerce.V1.service.impl;

import com.ecomerce.Ecomerce.V1.model.Brand;
import com.ecomerce.Ecomerce.V1.repository.BrandRepository;
import com.ecomerce.Ecomerce.V1.service.interfaces.BrandService;
import com.ecomerce.Ecomerce.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public Brand create(String name) {
        Optional<Brand> optionalBrand = brandRepository.findByName(name);

        if(optionalBrand.isPresent())
            throw new BadRequestException("O nome que você escolheu para sua marca já existe!");

        Brand brand = new Brand(name);
        brandRepository.save(brand);

        return brand;
    }
}
