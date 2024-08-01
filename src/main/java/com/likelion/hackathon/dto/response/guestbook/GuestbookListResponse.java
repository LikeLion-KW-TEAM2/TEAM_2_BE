package com.likelion.hackathon.dto.response.guestbook;

import java.util.List;

import com.likelion.hackathon.domain.Guestbook;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GuestbookListResponse {
    private String myImage;
    private List<Guestbook> guestbooks; 
}
