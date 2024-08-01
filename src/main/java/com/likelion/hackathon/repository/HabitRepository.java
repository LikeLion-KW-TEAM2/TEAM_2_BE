package com.likelion.hackathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.likelion.hackathon.domain.Habit;

public interface HabitRepository extends JpaRepository<Habit, Integer> {
    List<Habit> findAllByUserIdOrderByCreatedAtDesc(String UserId);

    List<Habit> findAllByUserId(String userId);

    Habit findByUserIdAndId(String userId, int habitId);

    Long countByUserId(String userId);

    void deleteById(int habitId);
    
    // overcome = 0 -> 극복 못 한 것만 리턴, overcome = 1 -> 극복 한 것만 리턴
    List<Habit> findAllByUserIdAndOvercome(String userId, int overcome);
}
