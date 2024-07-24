public class GuestBookService {
    private final UserRepository repository;

    public GuestBookResponse getGuestList(String userId) {
        User user = repository.findByUserId(userId);
        List<GuestBook> guestBookList = findAllByUserIdOrderByCreatedAtDesc(userId);
        return new GuestBookResponse(user.getImage, GuestBookResponse);
    }

    public void deleteGuestBook(int id) {
        deleteById(id);
    }
}
