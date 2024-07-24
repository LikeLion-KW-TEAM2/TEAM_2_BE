public class GuestBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String writer;
    private String userId;
    private String content;
    private String createdAt;
}
