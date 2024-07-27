package com.likelion.hackathon.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.likelion.hackathon.domain.Friend;
import com.likelion.hackathon.domain.Habit;
import com.likelion.hackathon.domain.History;
import com.likelion.hackathon.domain.User;
import com.likelion.hackathon.dto.request.user.EditPasswordRequest;
import com.likelion.hackathon.dto.request.user.EditinfoRequest;
import com.likelion.hackathon.dto.request.user.IdValidateRequest;
import com.likelion.hackathon.dto.request.user.SignupRequest;
import com.likelion.hackathon.dto.response.friend.FriendListElement;
import com.likelion.hackathon.dto.response.friend.FriendListResponse;
import com.likelion.hackathon.dto.response.user.DoneHabitResponse;
import com.likelion.hackathon.dto.response.user.IcecreamResponse;
import com.likelion.hackathon.dto.response.user.MypageProfileResponse;
import com.likelion.hackathon.repository.FriendRepository;
import com.likelion.hackathon.repository.GuestbookRepository;
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
    private final FriendRepository friendRepository;
    private final GuestbookRepository guestbookRepository;
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

    public IcecreamResponse getIcecream(String userId) {
        User user = userRepository.findByUserId(userId);
        return new IcecreamResponse(user.getName(), user.getImage(), user.getIcecream());
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
        friendRepository.deleteAllByUserId(userId);
        guestbookRepository.deleteAllByUserId(userId);
        userRepository.deleteByUserId(userId);
    }

    public List<FriendListElement> getFriendList(String userId) {
        List<Friend> friends = friendRepository.findAllByUserId(userId);
        List<FriendListElement> result = new ArrayList<>();
        for (Friend friend : friends) {
            // habitRepository.countByUserId(userId).intValue();
            result.add(new FriendListElement(friend.getFriendId(), friend.getName(), friend.getImage(),
                    habitRepository.countByUserId(friend.getFriendId()).intValue()));
        }
        return result;
    }

    public void deleteFriend(String userId, String friendId) {
        friendRepository.deleteByUserIdAndFriendId(userId, friendId);
    }
    
    public List<DoneHabitResponse> getDoneHabits(String userId) {
        List<Habit> habits = habitRepository.findAllByUserIdAndOvercome(userId, 1);
        List<DoneHabitResponse> result = new ArrayList<>();
        for (Habit habit : habits) {
            History lastHistory = historyRepository.findByHabitIdAndStatusOrderByDateDesc(habit.getId(), 2).get(0);
            result.add(new DoneHabitResponse(habit.getName(), habit.getCreatedAt(), lastHistory.getDate()));
        }
        return result;
    }
}
