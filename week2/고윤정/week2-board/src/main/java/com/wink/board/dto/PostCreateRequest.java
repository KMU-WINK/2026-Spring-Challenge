package com.wink.board.dto;

import jakarta.validation.constraints.*;

public record PostCreateRequest(
        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 20, message = "제목은 20자를 넘을 수 없습니다.")
        String title,

        @NotBlank(message = "내용은 필수입니다.")
        String content,

        @NotBlank(message = "작성자는 필수입니다.")
        String writer
) { }
