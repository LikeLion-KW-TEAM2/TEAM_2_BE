package com.likelion.hackathon.dto.response.user;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoneHabitResponse {
    String name;
    LocalDate startDate;
    LocalDate endDate;
}
