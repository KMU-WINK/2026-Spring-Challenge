package com.wink.board.exception;

public class DuplicateLikeException extends RuntimeException {
    public DuplicateLikeException() {
        super("이미 좋아요를 누른 게시글입니다.");
    }
}
