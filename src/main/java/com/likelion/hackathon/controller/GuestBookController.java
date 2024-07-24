package com.likelion.hackathon.controller;

@RestController
@RequestMapping("/guestbook")
public class GuestbookController {
    private final GuestbookService service;

    public GuestbookController(GuestbookService service) {
        this.service = service;
    }

    @GetMapping
    public GuestBookResponse getAllEntries() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return service.getGuestList(userId);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteGuestBook(@PathVariable int id) {

    }
}
