package com.wink.board.repository;

import com.wink.board.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    // 다음에 쓸 id. save로 그 id가 저장되기 전까지는 같은 값을 돌려준다
    Long nextId();
    Post save(Post post);
    List<Post> findAll();
    Optional<Post> findById(Long id);
    void deleteById(Long id);
}
