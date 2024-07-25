package com.likelion.hackathon.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.likelion.hackathon.domain.Habit;
import com.likelion.hackathon.domain.User;
import com.likelion.hackathon.dto.request.user.EditPasswordRequest;
import com.likelion.hackathon.dto.request.user.EditinfoRequest;
import com.likelion.hackathon.dto.request.user.IdValidateRequest;
import com.likelion.hackathon.dto.request.user.SignupRequest;
import com.likelion.hackathon.dto.response.user.MypageProfileResponse;
import com.likelion.hackathon.repository.HabitRepository;
import com.likelion.hackathon.repository.HistoryRepository;
import com.likelion.hackathon.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService{
    private final UserRepository userRepository;
    private final HabitRepository habitRepository;
    private final HistoryRepository historyRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository.findByUserId(userId);
    }

    public void signup(SignupRequest dto) {
        userRepository.save(User.builder()
                .userId(dto.getUserId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .agree(dto.getAgree())
                .icecream(0)
                .image("default")
                .role("ROLE_MEMBER")
                .build());
    }
    
    public boolean validate(IdValidateRequest dto) {
        return userRepository.existsByUserId(dto.getUserId());
    }

    public MypageProfileResponse editInfo(String userId, EditinfoRequest dto){
        User user = (User) loadUserByUsername(userId);
        user.setName(dto.getName());
        user.setImage(dto.getMyImage());
        return new MypageProfileResponse(user.getName(), user.getImage());
    }

    public void editPassword(String userId, EditPasswordRequest dto) {
        User user = (User) loadUserByUsername(userId);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
    }

    public void removeUser(String userId) {
        List<Habit> habits = habitRepository.findAllByUserId(userId);
        for (Habit habit : habits) {
            historyRepository.deleteAllByHabitId(habit.getId());
            habitRepository.deleteById(habit.getId());
        }
        userRepository.deleteByUserId(userId);
    }
}
