package com.likelion.hackathon.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditDefaultImageRequest {
    private String name;
    private String image;
}
