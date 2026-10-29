package com.wink.board.dto;

public record PostUpdateRequest(
        String title,
        String content
){}