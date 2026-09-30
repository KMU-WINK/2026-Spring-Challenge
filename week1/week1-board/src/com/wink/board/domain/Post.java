package com.wink.board.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Post {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private int likeCount;
    private String createdAt;

    public Post(Long id, String title, String content, String writer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));
        // 기본 좋아요 수 0으로 시작
        this.likeCount = 0;
    }

    public Long getId() {return id;}
    public String getTitle() {return title;}
    public String getContent() {return content;}
    public String getWriter() {return writer;}
    public int getLikeCount() {return likeCount;}
    public String getCreatedAt() {return createdAt;}

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void like() {
        this.likeCount++;
    }
}
