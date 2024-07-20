package com.likelion.hackathon.controller;

import org.springframework.web.bind.annotation.RestController;

import com.likelion.hackathon.dto.request.IdValidateRequest;
import com.likelion.hackathon.dto.request.SignupRequest;
import com.likelion.hackathon.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest dto) {
        service.signup(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 정상적으로 처리되었습니다.");
    }
    
    @PostMapping("/signup/idvalidate")
    public ResponseEntity<String> idValidate(@RequestBody IdValidateRequest dto) {
        boolean isValid = service.validate(dto);
        if (isValid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 사용중인 아이디 입니다.");
        }
        return ResponseEntity.ok("사용 가능한 아이디입니다.");
    }
}
