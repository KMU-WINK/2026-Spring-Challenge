package com.wink.board.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "post")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    protected Post() { }

    public Post(String title, String content, User user) {
        validateTitle(title);
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public User getUser() { return user; }

    public void update(String title, String content) {
        if (title != null) {
            validateTitle(title);
            this.title = title;
        }
        if (content != null) {
            this.content = content;
        }
    }

    private static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목은 비어 있을 수 없습니다.");
        }
        if (title.length() > 20) {
            throw new IllegalArgumentException("제목은 20자를 넘을 수 없습니다.");
        }
    }
}