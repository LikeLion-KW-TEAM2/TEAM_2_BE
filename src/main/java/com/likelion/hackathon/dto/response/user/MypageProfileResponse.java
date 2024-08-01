package com.likelion.hackathon.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MypageProfileResponse {
    private String name;
    private String myImage;
}
