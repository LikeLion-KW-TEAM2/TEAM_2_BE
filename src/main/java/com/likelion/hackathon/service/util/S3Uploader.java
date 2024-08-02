package com.likelion.hackathon.service.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3Uploader {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String userId) {
        String fileName = "images/" + userId;
        try {
            // String uploadImageUrl = putS3(multipartFile.getInputStream(), fileName, multipartFile.getSize());
            return putS3(multipartFile.getInputStream(), fileName, multipartFile.getSize());
        } catch (IOException e) {
            return "default";
        }
    }

    private String putS3(InputStream inputStream, String fileName, long contentLength) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(contentLength);

        amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3.getUrl(bucket, fileName).toString();
    }
}