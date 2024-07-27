package com.likelion.hackathon.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.likelion.hackathon.domain.Habit;
import com.likelion.hackathon.domain.History;
import com.likelion.hackathon.dto.request.habit.CheckHabitRequest;
import com.likelion.hackathon.dto.request.habit.CreateHabitRequest;
import com.likelion.hackathon.dto.request.habit.EditHabitRequest;
import com.likelion.hackathon.dto.response.habit.EditHabitPageResponse;
import com.likelion.hackathon.dto.response.habit.MainPageHabit;
import com.likelion.hackathon.repository.HabitRepository;
import com.likelion.hackathon.repository.HistoryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class HabitService {
    private final HabitRepository habitRepository;
    private final HistoryRepository historyRepository;

    public List<Habit> getAllHabits(String UserId) {
        return habitRepository.findAllByUserIdOrderByCreatedAtDesc(UserId);
    }

    // 메인화면 접속시 오늘 날짜에 해당하는 습관 정보 제공
    public List<MainPageHabit> getTodayHabits(String UserId) {
        List<Habit> allHabits = habitRepository.findAllByUserIdOrderByCreatedAtDesc(UserId);

        List<MainPageHabit> result = new ArrayList<>();

        for (Habit habit : allHabits) {
            if ((habit.getCreatedAt().isBefore(LocalDate.now())
                    || habit.getCreatedAt().isEqual(LocalDate.now()))
                    && habit.getOvercome() != 1) {
                int status = 1;
                History history = historyRepository.findByHabitIdAndDate(habit.getId(), LocalDate.now());
                if (history == null || history.getStatus() == 0) {
                    status = 0;
                }
                result.add(new MainPageHabit(habit.getId(), habit.getName(), status, habit.getCreatedAt()));
            }
        }
        return result;
    }

    // 선택한 날짜에 대한 습관 정보 제공
    public List<MainPageHabit> getHabitsByDate(String UserId, LocalDate date) {
        List<Habit> allHabits = habitRepository.findAllByUserIdOrderByCreatedAtDesc(UserId);

        List<MainPageHabit> result = new ArrayList<>();

        for (Habit habit : allHabits) {
            if ((habit.getCreatedAt().isBefore(date)
                    || habit.getCreatedAt().isEqual(date))
                    && habit.getOvercome() != 1) {
                int status = 1;
                History history = historyRepository.findByHabitIdAndDate(habit.getId(), date);
                if (history == null || history.getStatus() == 0) {
                    status = 0;
                }
                result.add(new MainPageHabit(habit.getId(), habit.getName(), status, habit.getCreatedAt()));
            }
        }
        return result;
    }

    // 습관 생성
    public void createHabit(String UserId, CreateHabitRequest dto) {
        habitRepository.save(Habit.builder()
                .userId(UserId)
                .name(dto.getName())
                .privacy(dto.getPrivacy())
                .periodType(dto.getPeriodType())
                .overcome(0)
                .build());
    }

    // 습관 수정 페이지 호출 시 기존 습관 정보 반환
    public EditHabitPageResponse getHabitById(String userId, int habitId) {
        Habit habit = habitRepository.findByUserIdAndId(userId, habitId);
        // int dDay = 
        int dDay = Period.between(LocalDate.now(), habit.getCreatedAt().plusDays(30)).getDays();
        return new EditHabitPageResponse(habit.getId(), habit.getName(), habit.getPeriodType(), habit.getPrivacy(),
                dDay);
    }

    // 습관 정보 수정
    public Habit editHabit(String userId, int habitId, EditHabitRequest dto) {
        Habit habit = habitRepository.findByUserIdAndId(userId, habitId);
        habit.setName(dto.getName());
        habit.setPeriodType(dto.getPeriodType());
        habit.setPrivacy(dto.getPrivacy());
        habit.setOvercome(dto.getOvercome());
        if (dto.getOvercome() == 1) {
            historyRepository.save(History.builder()
                    .habitId(habitId)
                    .date(LocalDate.now())
                    .status(2).build());
        }
        return habit;
    }

    // 습관 정보와 기록 삭제
    public void deleteHabit(int habitId) {
        historyRepository.deleteAllByHabitId(habitId);
        habitRepository.deleteById(habitId);
    }

    // 습관 여부 체크
    public void checkHabit(String userId, int habitId, CheckHabitRequest dto) {
        habitRepository.findByUserIdAndId(userId, habitId);
        History history = historyRepository.findByHabitIdAndDate(habitId, dto.getDate());
        if (history == null) {
            historyRepository.save(History.builder()
                    .habitId(habitId)
                    .date(dto.getDate())
                    .status(1).build());
        }
        if (history != null) {
            int status = history.getStatus();
            if (status == 0) {
                history.setStatus(1);
                return;
            }
            if (status == 1) {
                history.setStatus(0);
                return;
            }
        }
    }
}
