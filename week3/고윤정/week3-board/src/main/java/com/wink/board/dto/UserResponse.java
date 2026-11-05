package com.wink.board.dto;

import com.wink.board.domain.User;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public record UserResponse(
        Long id,
        String nickname,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime createdAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getNickname(), user.getCreatedAt());
    }
}