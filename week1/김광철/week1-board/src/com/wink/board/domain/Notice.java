package com.wink.board.domain;

public class Notice extends Post{

    public Notice(Long id, String title, String content, String writer, boolean isPost) {
        super(id, title, content, writer, isPost);
    }
}
