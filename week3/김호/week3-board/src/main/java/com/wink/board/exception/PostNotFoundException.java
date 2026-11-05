package com.wink.board.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long id) {
        super("The post not found: " + id);
    }
}

