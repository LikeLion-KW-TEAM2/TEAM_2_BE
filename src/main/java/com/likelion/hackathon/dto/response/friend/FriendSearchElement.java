package com.likelion.hackathon.dto.response.friend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendSearchElement {
    private String userId;
    private String name;
    private String image;
    private int isFriend;
}
