package com.wink.board.validator;

import com.wink.board.dto.PostCreateRequest;
import com.wink.board.exception.InvalidTitleException;

public final class ValidateParameters {
    private ValidateParameters() {
    }
    public static void validateTitle(PostCreateRequest request) throws InvalidTitleException {
        if(request.title().length() > 20) {
            throw new InvalidTitleException("The length of title must be less than 20.");
        }
        if(request.title().isBlank()) {
            throw new InvalidTitleException("The title must not be empty.");
        }
    }
}
