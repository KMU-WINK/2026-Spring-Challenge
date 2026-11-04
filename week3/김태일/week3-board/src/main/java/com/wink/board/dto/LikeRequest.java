package com.wink.board.dto;

import jakarta.validation.constraints.NotNull;

public record LikeRequest(
        @NotNull Long userId    // 로그인 기능이 없어서 누가 눌렀는지 직접 받음
) { }
