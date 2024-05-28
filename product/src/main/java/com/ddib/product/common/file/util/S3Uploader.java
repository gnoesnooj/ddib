package com.ddib.product.common.file.util;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3Uploader {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.prefix}")
    private String prefix;

    private final AmazonS3Client amazonS3Client;

    public List<String> storeImages(String domain, List<MultipartFile> files) {
        List<String> images = new ArrayList<>();
        try {// db 에 넣어줄 stirng list, mullable
            for (MultipartFile file : files) {
                log.info("저장 도메인 : {}", domain);
                String fileName = domain + "/" + S3UUIDGenerator.generateUUID(); // url 뒤에 붙을 s3에 저장될 위치, record 폴더에 저장되도록
                log.info("생성된 S3 파일명 : {}", fileName);
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(file.getContentType());
                metadata.setContentLength(file.getSize());
                amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
                images.add(prefix + fileName);
            }
        } catch (IOException e) {
            log.info("[ERROR] PRODUCT - 저장 실패");
            throw new RuntimeException(e);
        }
        return images;
    }

}
