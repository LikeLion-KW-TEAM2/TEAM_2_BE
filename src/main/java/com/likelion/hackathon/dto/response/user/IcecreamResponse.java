package com.likelion.hackathon.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IcecreamResponse {
    private String name;
    private String myImage;
    private int icecream;
}
