package com.wink.board.dto;

import com.wink.board.domain.Post;
import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        String title,
        String content,
        String writerNickname,        // 연관된 유저에서 꺼냄
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getNickname(),   // ← 여기서 지연 로딩 발생
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
