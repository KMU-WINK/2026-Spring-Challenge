package com.wink.board.validator;

import com.wink.board.exceptionHandler.InvalidTitleException;

public final class ValidateParameters {
    private ValidateParameters() {
    }
    public static void validateTitle(String title) throws InvalidTitleException {
        if(title.length() > 20) {
            throw new InvalidTitleException("The length of title must be less than 20.");
        }
        if(title.isEmpty()) {
            throw new InvalidTitleException("The length of title must be over than 0.");
        }
    }
}
