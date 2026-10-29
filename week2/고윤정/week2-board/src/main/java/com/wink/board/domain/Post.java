package com.wink.board.domain;

import java.time.LocalDateTime;

public class Post {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private int likeCount;
    private LocalDateTime createdAt;

    public Post(Long id, String title, String content, String writer) {
        validateTitle(title);
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.likeCount = 0;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getWriter() { return writer; }
    public int getLikeCount() { return likeCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void update(String title, String content) {
        validateTitle(title);
        this.title = title;
        this.content = content;
    }

    public void like() {
        this.likeCount++;
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목은 비어 있을 수 없습니다.");
        }
        if (title.length() > 20) {
            throw new IllegalArgumentException("제목은 20자 이내로 입력해주세요.");
        }
    }
}