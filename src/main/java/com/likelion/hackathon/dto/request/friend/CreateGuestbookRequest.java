package com.likelion.hackathon.dto.request.friend;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateGuestbookRequest {
    private String userId;
    private String content;
}
