package com.likelion.hackathon.dto.response.habit;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainPageResponse {
    private String myImage;
    private List<MainPageHabit> habits;
}
