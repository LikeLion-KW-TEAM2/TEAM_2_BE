package com.likelion.hackathon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.likelion.hackathon.domain.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    boolean existsByUserId(String userId);

    User findByUserId(String userId);
}
