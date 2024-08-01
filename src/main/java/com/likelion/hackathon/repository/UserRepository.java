package com.likelion.hackathon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.likelion.hackathon.domain.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    boolean existsByUserId(String userId);

    User findByUserId(String userId);

    User findByUserIdAndName(String userId, String name);

    void deleteByUserId(String userId);

    List<User> findByUserIdContainingOrderByUserIdAsc(String userId);
}
