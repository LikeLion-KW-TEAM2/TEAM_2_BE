package com.likelion.hackathon.dto.request.habit;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditHabitRequest {
    private String name;
    private int periodType;
    private int privacy;
    private int overcome;
}
