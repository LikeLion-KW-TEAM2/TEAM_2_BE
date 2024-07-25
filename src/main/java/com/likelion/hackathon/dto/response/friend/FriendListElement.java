package com.likelion.hackathon.dto.response.friend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendListElement {
    private String userid;
    private String name;
    private String iamge;
    private int count;
}
