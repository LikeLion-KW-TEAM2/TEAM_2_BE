package com.likelion.hackathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.likelion.hackathon.domain.Friend;

public interface FriendRepository extends JpaRepository<Friend, Integer>{
    List<Friend> findAllByUserId(String userId);

    void deleteAllByUserId(String userId);

    void deleteByUserIdAndFriendId(String userId, String friendId);
}
