package com.likelion.hackathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.likelion.hackathon.domain.Guestbook;

public interface GuestbookRepository extends JpaRepository<Guestbook, Integer>{
    List<Guestbook> findAllByUserIdOrderByCreatedAtDesc(String userId);

    void deleteByuserIdAndId(String userId, int id);
}
