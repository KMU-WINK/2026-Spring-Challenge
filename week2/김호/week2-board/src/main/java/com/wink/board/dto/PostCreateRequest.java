package com.wink.board.dto;

import jakarta.validation.constraints.*;

public record PostCreateRequest(
        @NotBlank(message = "Title must be needed.")
        @Size(max = 20, message = "Length of title should not be more than 20 characters.")
        String title,
        @NotBlank(message = "Content must be needed.")
        String content,
        @NotBlank(message = "Writer must be needed.")
        String writer
) { }