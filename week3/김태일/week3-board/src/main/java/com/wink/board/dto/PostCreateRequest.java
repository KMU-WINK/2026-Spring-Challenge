package com.wink.board.dto;

import jakarta.validation.constraints.*;

public record PostCreateRequest(
        @NotBlank @Size(max = 20) String title,
        @NotBlank String content,
        @NotNull Long userId
) { }