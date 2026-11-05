package com.wink.board.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("존재하지 않는 유저입니다. id=" + id);
    }
}