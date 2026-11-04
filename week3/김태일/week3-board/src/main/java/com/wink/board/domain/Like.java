package com.wink.board.domain;

import jakarta.persistence.*;

@Entity
@Table(
        name = "likes",     // like는 MySQL 예약어라 likes로
        uniqueConstraints = @UniqueConstraint(
                name = "uk_likes_user_post",
                columnNames = {"user_id", "post_id"}   // 같은 유저가 같은 글에 두 번 못 누르게 DB에서도 막음
        )
)
public class Like extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;      // 누가 눌렀는지

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;      // 어떤 글에 눌렀는지

    protected Like() { }

    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public Post getPost() { return post; }
}
