package com.likelion.hackathon.service.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileOutputStream;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3Uploader {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucket;

    public String upload(MultipartFile multipartFile, String userId) {
        try {
            String originalFileName = multipartFile.getOriginalFilename();
            // 이미지를 s3에 저장하기 위한 정보 설정(images/파일이름)
            String fileName = "images/" + userId;
            // MultipartFile를 file로 변환
            File uploadFile = new File(originalFileName);
            FileOutputStream fos = new FileOutputStream(uploadFile);
            fos.write(multipartFile.getBytes());

            // s3로 업로드
            String uploadImageUrl = putS3(uploadFile, fileName);

            // 업로드를 위해 임시로 스프링이 가지고 있던 이미지 파일 삭제
            uploadFile.delete();

            // 이미지의 위치에 대한 Url 반환
            return uploadImageUrl;
        } catch (Exception e) {
            return "default";
        }
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3.getUrl(bucket, fileName).toString();
    }
}