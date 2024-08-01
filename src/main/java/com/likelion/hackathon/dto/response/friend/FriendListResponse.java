package com.likelion.hackathon.dto.response.friend;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendListResponse {
    private String myImage;
    private List<FriendListElement> friends;
}
