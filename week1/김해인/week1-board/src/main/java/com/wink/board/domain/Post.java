package com.wink.board.domain;

import java.time.LocalDateTime;

//게시글 한 개를 표현하는 클래스
public class Post {

    //게시글의 정보 (다른 클래스가 건드릴 수 없게 private)
    private Long id;
    private String title;
    private String content;
    private String writer;
    private int likeCount;
    private final LocalDateTime createdAt; //final을 붙인 이유 : 게시물 최초 작성 시간 수정 불가

    //생성자
    public Post(Long id, String title, String content, String writer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.likeCount = 0;
        this.createdAt = LocalDateTime.now();
    }

    //변수가 private이므로 조회하기 위한 getter
    public Long getId() {return id;}
    public String getTitle() {return title;}
    public String getContent() {return content;}
    public String getWriter() {return writer;}
    public int getLikeCount() {return likeCount;}
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
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
