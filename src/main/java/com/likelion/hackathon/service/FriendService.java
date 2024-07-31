package com.likelion.hackathon.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.likelion.hackathon.domain.Friend;
import com.likelion.hackathon.domain.Guestbook;
import com.likelion.hackathon.domain.Habit;
import com.likelion.hackathon.domain.User;
import com.likelion.hackathon.dto.request.friend.CreateFriendRequest;
import com.likelion.hackathon.dto.request.friend.CreateGuestbookRequest;
import com.likelion.hackathon.dto.response.friend.FriendIcecreamResponse;
import com.likelion.hackathon.dto.response.friend.FriendListElement;
import com.likelion.hackathon.dto.response.friend.FriendListResponse;
import com.likelion.hackathon.dto.response.friend.FriendSearchElement;
import com.likelion.hackathon.repository.FriendRepository;
import com.likelion.hackathon.repository.GuestbookRepository;
import com.likelion.hackathon.repository.HabitRepository;
import com.likelion.hackathon.repository.UserRepository;
import com.likelion.hackathon.service.util.IcecreamScore;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final HabitRepository habitRepository;
    private final UserRepository userRepository;
    private final GuestbookRepository guestbookRepository;
    private final IcecreamScore icecreamScore;

    public FriendListResponse getFriendList(String userId) {
        List<Friend> friends = friendRepository.findAllByUserId(userId);
        List<FriendListElement> elements = new ArrayList<>();
        for (Friend friend : friends) {
            // habitRepository.countByUserId(userId).intValue();
            elements.add(new FriendListElement(friend.getFriendId(), friend.getName(), friend.getImage(),
                    habitRepository.countByUserId(friend.getFriendId()).intValue()));
        }
        return new FriendListResponse(userRepository.findByUserId(userId).getImage(), elements);
    }

    public List<FriendSearchElement> friendSearch(String id, String userId) {
        List<User> users = userRepository.findByUserIdContainingOrderByUserIdAsc(id);
        List<Friend> friends = friendRepository.findAllByUserId(userId);
        Set<String> friendIds = friends.stream()
                                   .map(Friend::getFriendId)
                                   .collect(Collectors.toSet());
        List<FriendSearchElement> result = new ArrayList<>();
        for (User user : users) {
            if (!user.getUserId().equals(userId)) {
                int isFriend = friendIds.contains(user.getUserId()) ? 1 : 0;
                result.add(new FriendSearchElement(user.getUserId(), user.getName(), user.getImage(), isFriend));
            }
        }
        return result;
    }
    
    public void createFriend(String userId, CreateFriendRequest dto) {
        User user = userRepository.findByUserIdAndName(dto.getUserId(), dto.getName());
        if (user != null) {
            friendRepository.save(Friend.builder()
                    .userId(userId)
                    .friendId(user.getUserId())
                    .name(user.getName())
                    .image(user.getImage())
                    .build());
        }
    }

    public FriendIcecreamResponse getFriendIcecream(String userId) {
        User user = userRepository.findByUserId(userId);
        List<Habit> habits = habitRepository.findAllByUserIdAndOvercome(userId, 0);
        List<String> elements = new ArrayList<>();
        for (Habit habit : habits) {
            if (habit.getPrivacy() == 0) {
                elements.add(habit.getName());
            }
        }
        user.setIcecream(icecreamScore.calculateIcecreamScore(userId));
        return new FriendIcecreamResponse(user.getName(), user.getIcecream(), elements);
    }
    
    public void createGuestbook(String name, CreateGuestbookRequest dto) {
        guestbookRepository.save(Guestbook.builder()
                .userId(dto.getUserId())
                .writer(name)
                .content(dto.getContent())
                .build());
    }
}
