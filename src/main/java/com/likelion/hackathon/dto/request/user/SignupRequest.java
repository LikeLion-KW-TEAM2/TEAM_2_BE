package com.likelion.hackathon.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupRequest {
    private String userId;
    private String password;
    private String name;
    private int agree;
}
