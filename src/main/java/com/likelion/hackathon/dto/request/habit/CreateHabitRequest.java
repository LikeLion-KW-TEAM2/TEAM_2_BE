package com.likelion.hackathon.dto.request.habit;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateHabitRequest {
    private String name;
    private int periodType;
    private int privacy;
}
