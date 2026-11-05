package com.wink.board.dto;

import jakarta.validation.constraints.*;

// 게시글 작성: writer → userId
public record PostCreateRequest(
        @NotBlank @Size(max = 20) String title,
        @NotBlank String content,
        @NotNull Long userId
) { }