package com.likelion.hackathon.dto.request.habit;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckHabitRequest {
    private LocalDate date;
}
