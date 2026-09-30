package com.wink.board.exceptionHandler;

public class InvalidPostException extends RuntimeException{
    public InvalidPostException(String message) {
        super("The post not found: " + message);
    }
}
