package com.likelion.hackathon.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditDefaultImageRequest {
    private String name;
    private String image;
}
