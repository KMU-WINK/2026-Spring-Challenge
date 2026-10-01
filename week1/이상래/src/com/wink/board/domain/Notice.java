package com.wink.board.domain;

public class Notice extends Post {

    public Notice(Long id, String title, String content, String writer) {
        super(id, title, content, writer);
    }

    @Override
    public String displayTitle() {
        return "[공지] " + getTitle();
    }

    @Override
    public boolean isNotice() {
        return true;
    }
}
