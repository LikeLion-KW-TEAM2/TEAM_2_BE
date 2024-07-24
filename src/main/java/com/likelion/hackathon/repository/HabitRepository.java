package com.likelion.hackathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.likelion.hackathon.domain.Habit;

public interface HabitRepository extends JpaRepository<Habit, Integer> {
    List<Habit> findAllByUserIdOrderByCreatedAtDesc(String UserId);

    Habit findByUserIdAndId(String userId, int habitId);

    void deleteById(int habitId);
}
