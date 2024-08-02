package com.likelion.hackathon.service.util;

import java.time.LocalDate;
import java.time.Period;
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
        for (Habit habit : ongoingHabit) {
            int period = Period.between(habit.getCreatedAt().minusDays(1), LocalDate.now()).getDays();
            System.out.println(period);
            if (period > 30) {
                totalDate += 30;
            }
            if (period <= 30) {
                totalDate += period;
            }
            checkedDate += historyRepository
                    .findAllByHabitIdAndStatusAndDateAfter(habit.getId(), 1, LocalDate.now()
                            .minusDays(30))
                    .size();
        }
        System.out.println("total : " + totalDate);
        System.out.println("check : " + checkedDate);
        float icereamStatusScore = 0;
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
