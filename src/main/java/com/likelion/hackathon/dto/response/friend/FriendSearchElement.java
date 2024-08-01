package com.likelion.hackathon.dto.response.friend;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendSearchElement {
    private String userId;
    private String name;
    private String image;
    private int isFriend;
}
