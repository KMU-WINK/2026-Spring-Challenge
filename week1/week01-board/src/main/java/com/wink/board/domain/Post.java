package com.wink.board.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Post {

    private final Long id;
    private String title;
    private String content;
    private final String writer;
    private int likeCount;
    private final LocalDateTime createdAt;

    public Post(Long id, String title, String content, String writer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.likeCount = 0;
        this.createdAt = LocalDateTime.now();
    }

    // 조회용 getter
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getWriter() { return writer; }
    public int getLikeCount() { return likeCount; }
    public String getCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return createdAt.format(formatter);
    }

    // 행동 (setter 대신)
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void like() {
        this.likeCount++;
    }
}
