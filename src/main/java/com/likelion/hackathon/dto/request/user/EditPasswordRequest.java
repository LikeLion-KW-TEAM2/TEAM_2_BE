package com.likelion.hackathon.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditPasswordRequest {
    private String password;
}
