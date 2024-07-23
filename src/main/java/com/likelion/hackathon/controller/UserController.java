package com.likelion.hackathon.controller;

import org.springframework.web.bind.annotation.RestController;

import com.likelion.hackathon.domain.User;
import com.likelion.hackathon.dto.request.user.EditImageRequest;
import com.likelion.hackathon.dto.request.user.EditPasswordRequest;
import com.likelion.hackathon.dto.request.user.EditinfoRequest;
import com.likelion.hackathon.dto.request.user.IdValidateRequest;
import com.likelion.hackathon.dto.request.user.SignupRequest;
import com.likelion.hackathon.dto.response.user.MypageProfileResponse;
import com.likelion.hackathon.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/mypage")
    public ResponseEntity getMypage() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) service.loadUserByUsername(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("프로필을 불러올 수 없습니다.");
        }
        return ResponseEntity.ok(new MypageProfileResponse(user.getName(), user.getImage()));
    }

    @GetMapping("/mypage/edit")
    public ResponseEntity getEditPage() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) service.loadUserByUsername(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("프로필을 불러올 수 없습니다.");
        }
        return ResponseEntity.ok(new MypageProfileResponse(user.getName(), user.getImage()));
    }

    @PostMapping("/mypage/edit")
    public ResponseEntity<MypageProfileResponse> editInfo(@RequestBody EditinfoRequest dto) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(service.editInfo(userId, dto));
    }

    // @PostMapping("/mypage/edit/image")
    // public ResponseEntity editImage(@RequestBody EditImageRequest dto) {
    //     // TODO: process POST request

    //     return ResponseEntity.ok();
    // }

    @PostMapping("/mypage/edit/password")
    public ResponseEntity<String> editPassword(@RequestBody EditPasswordRequest dto) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) service.loadUserByUsername(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 정보가 올바르지 않습니다.");
        }
        service.editPassword(userId, dto);
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }
}
