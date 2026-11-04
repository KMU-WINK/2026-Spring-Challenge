package com.wink.board.repository;

import com.wink.board.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByPostIdAndUserId(Long postId, Long userId);

    @Modifying
    @Query("delete from Like l where l.post.id = :postId and l.user.id = :userId")
    void deleteForUser(@Param("postId") Long postId, @Param("userId") Long userId);

    @Modifying
    @Query("delete from Like l where l.post.id = :postId")
    void deleteForPost(@Param("postId") Long postId);
}
