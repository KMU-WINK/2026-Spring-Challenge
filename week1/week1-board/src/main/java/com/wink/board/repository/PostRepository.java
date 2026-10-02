package com.wink.board.repository;

import com.wink.board.domain.Post;
import java.util.List;
import java.util.Optional;

//게시글 저장소의 필수 4가지 기능 만들기
public interface PostRepository {
    Post save(Post post); //게시글 저장
    List<Post> findAll(); //전체 게시글 조회
    Optional<Post> findById(Long id); //번호로 찾을 수 있어야함
    void deleteById(Long id); //삭제할 수 있어야함
}

