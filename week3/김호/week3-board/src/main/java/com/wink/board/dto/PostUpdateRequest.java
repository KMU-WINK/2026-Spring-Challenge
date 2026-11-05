package com.wink.board.dto;

import jakarta.validation.constraints.Size;

// 게시글 수정: PATCH라서 null 허용 (1장 Q1)
public record PostUpdateRequest(
        @Size(max = 20) String title,
        String content
) { }