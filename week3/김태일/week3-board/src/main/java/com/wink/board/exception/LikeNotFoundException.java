package com.wink.board.exception;

public class LikeNotFoundException extends RuntimeException {
    public LikeNotFoundException(Long userId, Long postId) {
        super("좋아요를 누르지 않은 게시글입니다. userId=" + userId + ", postId=" + postId);
    }
}
