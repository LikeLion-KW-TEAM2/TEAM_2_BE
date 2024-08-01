package com.likelion.hackathon.dto.response.habit;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditHabitPageResponse {
    private int id;
    private String name;
    private int periodType;
    private int privacy;
    private int dDay;
}
