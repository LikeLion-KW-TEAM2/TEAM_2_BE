package com.likelion.hackathon.dto.response.friend;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendListElement {
    private String userId;
    private String name;
    private String iamge;
    private int count;
}
