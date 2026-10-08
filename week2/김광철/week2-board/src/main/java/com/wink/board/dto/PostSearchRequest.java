package com.wink.board.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record PostSearchRequest(
        String write,
        @Min(0) Integer page,
        @Min(1) @Max(100) Integer size
) {
    public PostSearchRequest {
        page = page == null ? 0 : page;
        size = size == null ? 10 : size;
    }
}