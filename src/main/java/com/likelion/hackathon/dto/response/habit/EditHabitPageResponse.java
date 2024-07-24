package com.likelion.hackathon.dto.response.habit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditHabitPageResponse {
    private int id;
    private String name;
    private int periodType;
    private int privacy;
    private int dDay;
}
