package com.wink.board.domain;

import com.wink.board.exception.InvalidPostException;

import java.time.LocalDateTime;

public class Post {

    private static final int MAX_TITLE_LENGTH = 20;

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

    // 조회용 getter
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getWriter() { return writer; }
    public int getLikeCount() { return likeCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // 행동 (setter 대신)
    public void update(String title, String content) {
        validateTitle(title);   // 작성할 때와 같은 규칙을 수정할 때도 적용
        this.title = title;
        this.content = content;
    }

    public void like() {
        this.likeCount++;
    }

    private static void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new InvalidPostException("제목은 비어 있을 수 없습니다.");
        }
        if (title.length() > MAX_TITLE_LENGTH) {
            throw new InvalidPostException("제목은 " + MAX_TITLE_LENGTH + "자 이하여야 합니다.");
        }
    }
}
