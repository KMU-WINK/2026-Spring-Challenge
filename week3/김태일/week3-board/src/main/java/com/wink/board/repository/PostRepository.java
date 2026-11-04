package com.wink.board.repository;

import com.wink.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitleContaining(String keyword);

    @Query("select p from Post p join fetch p.user")
    List<Post> findAllWithUser();
}