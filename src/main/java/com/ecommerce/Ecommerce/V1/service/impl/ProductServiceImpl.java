package com.ecommerce.Ecommerce.V1.service.impl;

import com.ecommerce.Ecommerce.V1.dto.offer.OfferRequest;
import com.ecommerce.Ecommerce.V1.dto.offer.OfferResponse;
import com.ecommerce.Ecommerce.V1.dto.product.ProductRequest;
import com.ecommerce.Ecommerce.V1.dto.product.ProductResponse;
import com.ecommerce.Ecommerce.V1.model.Account;
import com.ecommerce.Ecommerce.V1.model.Brand;
import com.ecommerce.Ecommerce.V1.model.Offer;
import com.ecommerce.Ecommerce.V1.model.Product;
import com.ecommerce.Ecommerce.V1.repository.BrandRepository;
import com.ecommerce.Ecommerce.V1.repository.OfferRepository;
import com.ecommerce.Ecommerce.V1.repository.ProductRepository;
import com.ecommerce.Ecommerce.V1.service.interfaces.AccountService;
import com.ecommerce.Ecommerce.V1.service.interfaces.BrandService;
import com.ecommerce.Ecommerce.V1.service.interfaces.ProductService;
import com.ecommerce.Ecommerce.V1.util.AccountUtil;
import com.ecommerce.Ecommerce.V1.util.CloudUtil;
import com.ecommerce.Ecommerce.V1.util.ConversionUtil;
import com.ecommerce.Ecommerce.V1.util.OfferUtil;
import com.ecommerce.Ecommerce.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final AccountService accountService;
    private final BrandRepository brandRepository;
    private final BrandService brandService;
    private final OfferRepository offerRepository;

    @Override
    public ProductResponse create(ProductRequest request, MultipartFile file) throws IOException {
        Account account = accountService.accountContext();
        if(!AccountUtil.containRole(account.getRole(), "BRAND"))
            throw new BadRequestException("A criação de produtos é reservada apenas para marcas!");

        return brandService.addProduct(account.getBrand(),request,file);
    }

    @Override
    public List<ProductResponse> findByBrandName(String brandName) {
        Brand brand = brandRepository.findByNameIgnoreCase(brandName)
                .orElseThrow(()-> new BadRequestException("Essa marca não existe!"));

        return brand.getProduct()
                .stream()
                .map(m-> ProductResponse
                        .builder()
                        .id(m.getId())
                        .name(m.getName())
                        .color(m.getColor())
                        .offer(OfferUtil.buildOfferResponse(m.getOffer()))
                        .descriptionEn(m.getDescriptionEn())
                        .descriptionBr(m.getDescriptionBr())
                        .gender(m.getGender())
                        .priceReal("R$" + ConversionUtil.formatMoney(m.getPriceReal()))
                        .priceDollar("$" + ConversionUtil.formatMoney(m.getPriceDollar()))
                        .profile(m.getProfile())
                        .build()).collect(Collectors.toList());
    }

    @Override
    public String deleteAll() throws IOException {
        Brand brand = accountService.accountContext().getBrand();

        if(brand == null)
            throw new BadRequestException("Você não pode deletar produtos pois não possui uma marca!");

        if(brand.getProduct() == null || brand.getProduct().size() == 0) {
            throw new BadRequestException("Você não possui produtos para deletar");
        }

        List<Product> productList = brand.getProduct();
        brand.setProduct(null);
        brandRepository.save(brand);

        for(Product product : productList){
            if(product.getProfile() != null)
                CloudUtil.deleteImage(product.getProfile());
            productRepository.delete(product);
        }

        return "Seus produtos foram removidos com Sucesso!";
    }

    @Override
    public OfferResponse createOffer(OfferRequest offerRequest) {
        Brand brand = accountService.accountContext().getBrand();
        Product product = null;
        boolean flag = false;
        for(Product p : brand.getProduct()){
            if (p.getName().equals(offerRequest.getProductName())) {
                flag = true;
                product = p;
                break;
            }
        }
        if(!flag)
            throw new BadRequestException("Sua marca não possui esse produto!");

        if(offerRequest.getExpiration().isBefore(LocalDateTime.now()))
            throw new BadRequestException("Informe uma data de expiração válida!");

        BigDecimal priceInDollar = product.getPriceDollar();
        BigDecimal percentage = new BigDecimal(String.valueOf(offerRequest.getPercentage()));
        percentage = percentage.divide(new BigDecimal("100"));
        BigDecimal offerPriceDollar = priceInDollar.subtract(priceInDollar.multiply(percentage));

        Offer offer = Offer
                .builder()
                .id(product.getOffer().getId())
                .status(true)
                .percentage(offerRequest.getPercentage())
                .expiration(offerRequest.getExpiration())
                .offerPriceDollar(offerPriceDollar)
                .offerPriceReal(ConversionUtil.dollarToReal(offerPriceDollar))
                .build();

        offerRepository.save(offer);

        return OfferResponse
                .builder()
                .id(offer.getId())
                .status(offer.getStatus())
                .percentage(offer.getPercentage() + "% Off")
                .expiration(offer.getExpiration())
                .offerPriceDollar("$" + ConversionUtil.formatMoney(offer.getOfferPriceDollar()))
                .offerPriceReal("R$" + ConversionUtil.formatMoney(offer.getOfferPriceReal()))
                .build();

    }


}
