package com.ecommerce.Ecommerce.V1.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Slf4j
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

    public static void deleteImage(String linkImage){
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dqpeupeg6",
                "api_key", "913252413593367",
                "api_secret", "-eqGFGm0ElfpPv2I7mUEjpjjT-s"
        ));

        String fileId = extractImageId(linkImage);
        log.info(fileId);

        try {
            cloudinary.uploader().destroy(fileId,ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String extractImageId(String linkImage){
        char[] charsLink = linkImage.toCharArray();
        char[] id = new char[36];

        int indexLink = charsLink.length -1;
        int indexId = 35;
        char c;
        while (true){
            c = charsLink[indexLink];

            if(c == '.'){
                while (indexId >= 0){
                    indexLink--;
                    c = charsLink[indexLink];
                    id[indexId] = c;
                    indexId--;
                }
                break;
            }
            indexLink--;
        }
        log.info(Arrays.toString(id));
        return new String(id);
    }
}
