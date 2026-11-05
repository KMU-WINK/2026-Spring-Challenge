package com.wink.board.dto;

import jakarta.validation.constraints.Size;

public record PostUpdateRequest(
        @Size(max = 20, message = "제목은 20자를 넘을 수 없습니다.")
        String title,

        String content
) { }
