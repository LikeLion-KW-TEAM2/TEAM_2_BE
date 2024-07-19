package com.likelion.hackathon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    private String userId;
    private String password;
    private String name;
    private int agree;
}
