package com.likelion.hackathon.dto.request.user;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditImageRequest {
    private String name;
    private MultipartFile image;
}
