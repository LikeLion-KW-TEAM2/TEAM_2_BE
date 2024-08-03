package com.likelion.hackathon.service.util;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.likelion.hackathon.domain.Habit;
import com.likelion.hackathon.repository.HabitRepository;
import com.likelion.hackathon.repository.HistoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class IcecreamScore {
    private final HabitRepository habitRepository;
    private final HistoryRepository historyRepository;

    public int calculateIcecreamScore(String userId){
        List<Habit> ongoingHabit = habitRepository.findAllByUserIdAndOvercome(userId, 0);
        float totalDate = 0;
        float checkedDate = 0;
        LocalDate today = LocalDate.now();
        LocalDate referenceDate = LocalDate.of(today.getYear(), today.getMonth().minus(1),
                today.getMonth().minus(1).length(false));
        float icereamStatusScore = 0;

        for (Habit habit : ongoingHabit) {
            if (today.getMonth() == habit.getCreatedAt().getMonth()) {
                totalDate += (today.lengthOfMonth() - habit.getCreatedAt().getDayOfMonth() + 1);
            }
            if (today.getMonth() != habit.getCreatedAt().getMonth()) {
                totalDate += today.lengthOfMonth();
            }
            checkedDate += historyRepository
                    .findAllByHabitIdAndStatusAndDateAfter(habit.getId(), 1,
                            referenceDate)
                    .size();
        }

        try {
            icereamStatusScore = 100 * (1 - (checkedDate / totalDate));
        } catch (ArithmeticException e) {
            icereamStatusScore = 0;
        }
        if (icereamStatusScore < 1) {
            return 0;
        }
        return (int) ((icereamStatusScore - 1) / 20  + 1);
    }
}
