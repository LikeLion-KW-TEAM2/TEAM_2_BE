package com.likelion.hackathon.dto.request.habit;

import java.time.LocalDate;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class CheckHabitRequest {
    private LocalDate date;
}
