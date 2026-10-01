package com.wink.board.domain;
import java.time.LocalDateTime;

public class Post {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private int likeCount;
    private LocalDateTime createdAt;

    public Post(Long id, String title, String content, String writer){
        this.id=id;
        this.title=title;
        this.content=content;
        this.writer=writer;
        this.likeCount=0;
        this.createdAt=LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getWriter() { return writer; }
    public int getLikeCount() { return likeCount; }

    public void update(String title, String content){
        this.title = title;
        this.content=content;
    }

    public void like() {
        this.likeCount++;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isNotice() {
        return false;
    }
}
