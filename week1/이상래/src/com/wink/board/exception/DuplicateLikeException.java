package com.wink.board.exception;

public class DuplicateLikeException extends RuntimeException {

    public DuplicateLikeException(String userName, Long postId) {
        super(userName + "님은 이미 " + postId + "번 게시글에 좋아요를 눌렀습니다.");
    }
}
