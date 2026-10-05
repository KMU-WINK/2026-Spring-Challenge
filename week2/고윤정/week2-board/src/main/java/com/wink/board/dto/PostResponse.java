package com.wink.board.dto;

import com.wink.board.domain.Post;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public record PostResponse(
        Long id,
        String title,
        String content,
        String writer,
        int likeCount,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime createdAt
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getWriter(),
                post.getLikeCount(),
                post.getCreatedAt()
        );
    }
}
