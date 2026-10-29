package com.wink.board.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String message) {
        super("The post not found: " + message);
    }
}

