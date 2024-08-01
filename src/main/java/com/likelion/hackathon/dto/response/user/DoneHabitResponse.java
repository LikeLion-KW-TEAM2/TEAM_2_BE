package com.likelion.hackathon.dto.response.user;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoneHabitResponse {
    String name;
    LocalDate startDate;
    LocalDate endDate;
}
