package com.likelion.hackathon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.likelion.hackathon.domain.Guestbook;
import com.likelion.hackathon.domain.User;
import com.likelion.hackathon.dto.response.guestbook.GuestbookListResponse;
import com.likelion.hackathon.repository.GuestbookRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GuestbookService {
    private final GuestbookRepository guestbookRepository;

    public GuestbookListResponse getGuestbookList(User user) {
        List<Guestbook> elements = guestbookRepository.findAllByUserIdOrderByCreatedAtDesc(user.getUserId());
        return new GuestbookListResponse(user.getImage(), elements);
    }

    public void deleteGuestbook(String userId, int id) {
        guestbookRepository.deleteByuserIdAndId(userId, id);
    }
}
