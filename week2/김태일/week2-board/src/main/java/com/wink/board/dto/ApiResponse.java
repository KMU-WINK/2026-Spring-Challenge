package com.wink.board.dto;

/**
 * 모든 API 응답의 공통 포맷
 * { "success": true, "data": {...}, "message": "..." }
 */
public record ApiResponse<T>(
        boolean success,
        T data,
        String message
) {
    // 성공 응답
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, data, message);
    }

    // 실패 응답 (data는 null)
    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(false, null, message);
    }
}
