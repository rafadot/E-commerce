package com.ecomerce.Ecomerce.V1.service.impl;

import com.ecomerce.Ecomerce.V1.model.Product;
import com.ecomerce.Ecomerce.V1.repository.ProductRepository;
import com.ecomerce.Ecomerce.V1.service.interfaces.ProductService;
import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product create(MultipartFile file) throws IOException {
        //cria o cliente de armazenamento
        Storage storage = StorageOptions.getDefaultInstance().getService();

        //nomeia do arquivo no Google Cloud Storage
        String fileName = UUID.randomUUID().toString();
        BlobId blobId = BlobId.of("e-commerce-382004.appspot.com",fileName);

        //carrega o arquivo de imagem do MultipartFile para o Google Cloud Storage
        byte[] bytes = file.getBytes();
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        Blob blob = storage.create(blobInfo,bytes);

        //define url
        Product product = new Product();
        product.setProfile(blob.getMediaLink());

        productRepository.save(product);
        return product;
    }
}
