package com.wink.board.exceptionHandler;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String message) {
        super("The post not found: " + message);
    }
}

