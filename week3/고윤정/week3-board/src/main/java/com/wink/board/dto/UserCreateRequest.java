package com.wink.board.dto;

import jakarta.validation.constraints.*;

public record UserCreateRequest(
        @NotBlank(message = "닉네임은 필수입니다.")
        @Size(max = 20, message = "닉네임은 20자를 넘을 수 없습니다.")
        String nickname
) { }