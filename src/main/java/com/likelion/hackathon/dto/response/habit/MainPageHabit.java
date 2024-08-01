package com.likelion.hackathon.dto.response.habit;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MainPageHabit {
    private int id;
    private String name;
    private int status;
    private LocalDate createdAt;
}
