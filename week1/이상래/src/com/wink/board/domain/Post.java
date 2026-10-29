package com.wink.board.domain;

import com.wink.board.exception.DuplicateLikeException;
import com.wink.board.exception.InvalidPostException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

// 파일에 객체를 통째로 저장(직렬화)하려면 Serializable을 구현해야 한다
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int MAX_TITLE_LENGTH = 20;

    private Long id;
    private String title;
    private String content;
    private User writer;
    // 숫자만 세면 누가 눌렀는지 알 수 없다 → 누른 사람을 Set으로 기억
    private Set<User> likedUsers;
    private LocalDateTime createdAt;

    public Post(Long id, String title, String content, User writer) {
        validateTitle(title);
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.likedUsers = new HashSet<>();
        this.createdAt = LocalDateTime.now();
    }

    // 조회용 getter
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public User getWriter() { return writer; }
    public int getLikeCount() { return likedUsers.size(); }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // 목록·상세에 보일 제목. Notice가 오버라이딩한다
    public String displayTitle() {
        return title;
    }

    // instanceof로 타입을 묻지 않고, 객체에게 직접 물어본다
    public boolean isNotice() {
        return false;
    }

    // 행동 (setter 대신)
    public void update(String title, String content) {
        validateTitle(title);   // 작성할 때와 같은 규칙을 수정할 때도 적용
        this.title = title;
        this.content = content;
    }

    public void like(User user) {
        // Set.add는 이미 들어 있으면 false를 돌려준다
        if (!likedUsers.add(user)) {
            throw new DuplicateLikeException(user.getName(), id);
        }
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
