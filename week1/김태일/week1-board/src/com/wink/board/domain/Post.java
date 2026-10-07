package com.wink.board.domain;

import com.wink.board.exception.DuplicateLikeException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;                 // 저장소가 save 시점에 부여
    private String title;
    private String content;
    private final User writer;
    private final Set<User> likedUsers = new HashSet<>();
    private final LocalDateTime createdAt;

    public Post(String title, String content, User writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdAt = LocalDateTime.now();
    }

    // 조회용 getter
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public User getWriter() { return writer; }
    public int getLikeCount() { return likedUsers.size(); }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // id는 저장소만 한 번 부여할 수 있음
    public void assignId(Long id) {
        if (this.id != null) {
            throw new IllegalStateException("이미 id가 부여된 게시글입니다. id=" + this.id);
        }
        this.id = id;
    }

    // 행동 (setter 대신)
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void like(User user) {
        if (!likedUsers.add(user)) {   // Set.add는 이미 있으면 false
            throw new DuplicateLikeException(
                    user.getName() + "님은 이미 이 글에 좋아요를 눌렀습니다.");
        }
    }
}
