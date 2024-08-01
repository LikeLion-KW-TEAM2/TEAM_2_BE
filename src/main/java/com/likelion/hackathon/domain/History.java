package com.likelion.hackathon.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "habit_id")
    private int habitId;

    @Column(name = "date")
    private LocalDate date;
    
    // 체크 안 됨(못 지킴) : 0, 체크(지킴) : 1, 극복함 : 2
    @Column(name = "status")
    private int status;
}
