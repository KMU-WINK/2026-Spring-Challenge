package com.우리동아리.board.repository;

import com.우리동아리.board.domain.Post;
import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);
    List<Post> findAll();
    Optional<Post> findById(Long id);
    void deleteById(Long id);
}