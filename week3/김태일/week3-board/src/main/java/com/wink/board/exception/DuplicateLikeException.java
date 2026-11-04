package com.wink.board.exception;

public class DuplicateLikeException extends RuntimeException {
    public DuplicateLikeException(Long userId, Long postId) {
        super("이미 좋아요를 누른 게시글입니다. userId=" + userId + ", postId=" + postId);
    }
}
