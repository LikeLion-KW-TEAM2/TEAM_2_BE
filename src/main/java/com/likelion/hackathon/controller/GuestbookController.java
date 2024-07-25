package com.likelion.hackathon.controller;

import org.springframework.web.bind.annotation.RestController;

import com.likelion.hackathon.domain.User;
import com.likelion.hackathon.dto.response.guestbook.GuestbookListResponse;
import com.likelion.hackathon.service.GuestbookService;
import com.likelion.hackathon.service.UserService;

import lombok.RequiredArgsConstructor;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequiredArgsConstructor
public class GuestbookController {
    private final UserService userService;
    private final GuestbookService guestbookService;

    @GetMapping("/guestbook")
    public ResponseEntity getMethodName() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) userService.loadUserByUsername(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 정보가 올바르지 않습니다.");
        }
        GuestbookListResponse result = guestbookService.getGuestbookList(user);
        if (result.getGuestbooks().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("방명록이 존재하지 않습니다.");
        }
        return ResponseEntity.ok(result);
    }
    
    @DeleteMapping("/guestbook/delete/{id}")
    public ResponseEntity deleteGuestbook(@PathVariable int id) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) userService.loadUserByUsername(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자 정보가 올바르지 않습니다.");
        }
        guestbookService.deleteGuestbook(userId, id);
        return ResponseEntity.ok("방명록이 삭제되었습니다.");
    }
}
