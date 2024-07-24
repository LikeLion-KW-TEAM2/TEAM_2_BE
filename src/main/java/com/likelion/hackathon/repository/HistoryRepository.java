package com.likelion.hackathon.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.likelion.hackathon.domain.History;

public interface HistoryRepository extends JpaRepository<History, Integer>{
    List<History> findAllByHabitIdAndDate(int habitId, LocalDate date);

    History findByHabitIdAndDate(int habitId, LocalDate date);

    void deleteAllByHabitId(int habitId);
}
