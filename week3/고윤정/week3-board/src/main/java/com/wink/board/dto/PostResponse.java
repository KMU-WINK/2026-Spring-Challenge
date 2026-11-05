package com.wink.board.dto;

import com.wink.board.domain.Post;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public record PostResponse(
        Long id,
        String title,
        String content,
        String writerNickname,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime updatedAt
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getNickname(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
