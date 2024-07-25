package com.likelion.hackathon.dto.response.friend;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendIcecreamResponse {
    private String name;
    private int icecream;
    private List<String> habits;
}
