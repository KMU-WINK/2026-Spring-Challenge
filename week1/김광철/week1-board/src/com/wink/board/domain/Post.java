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
    private boolean isNotice;

    public Post(Long id, String title, String content, String writer, Boolean isNotice) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.likeCount = 0;
        this.isNotice = isNotice;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초");

        this.createdAt = LocalDateTime.now().format(formatter);
    }

    // 파일 복원용 생성자
    public Post(Long id, String title, String content, String writer, String createdAt, Boolean isNotice, int likeCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdAt = createdAt;
        this.isNotice = isNotice;
        this.likeCount = likeCount;
    }

    // 조회용 getter
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getWriter() { return writer; }
    public int getLikeCount() { return likeCount; }
    public String getCreatedAt() { return createdAt; }
    public Boolean getIsNotice() { return isNotice; }

    // 행동 (setter 대신)
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void like() {
        this.likeCount++;
    }
}