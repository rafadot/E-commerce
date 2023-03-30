package com.ecomerce.Ecomerce.V1.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class CloudUtil {
    public static String uploadImage(MultipartFile file) throws IOException {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dqpeupeg6",
                "api_key", "913252413593367",
                "api_secret", "-eqGFGm0ElfpPv2I7mUEjpjjT-s"
        ));

        String fileName = UUID.randomUUID().toString();

        Map upload = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("public_id", fileName));

        return upload.get("secure_url").toString();
    }
}
