package com.likelion.hackathon.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.likelion.hackathon.domain.User;
import com.likelion.hackathon.dto.request.IdValidateRequest;
import com.likelion.hackathon.dto.request.SignupRequest;
import com.likelion.hackathon.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService{
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void signup(SignupRequest dto) {
        repository.save(User.builder()
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
        return repository.existsByUserId(dto.getUserId());
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return repository.findByUserId(userId);
    }
}
