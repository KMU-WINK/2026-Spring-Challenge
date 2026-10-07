package com.wink.board.domain;

public class Post {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private int likeCount;

    //생성자
    public Post(Long id, String title, String content, String writer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.likeCount = 0;
    }

    //변수가 private이므로 조회하기 위한 getter
    public Long getId() {return id;}
    public String getTitle() {return title;}
    public String getContent() {return content;}
    public String getWriter() {return writer;}
    public int getLikeCount() {return likeCount;}

    //게시글 수정
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    //좋아요 호출되면, 좋아요 1증가
    public void like() {
        this.likeCount++;
    }
}

