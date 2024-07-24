public interface GuestBookRepository extends JpaRepository<GuestBook, Integer> {
    List<GuestBook> findAllByUserIdOrderByCreatedAtDesc(String UserId);
    void deleteById(int id);

}