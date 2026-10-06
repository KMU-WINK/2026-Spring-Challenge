package com.wink.board.domain;

public class Notice extends Post {

    private static final long serialVersionUID = 1L;

    public Notice(String title, String content, User writer) {
        super(title, content, writer);
    }
}
