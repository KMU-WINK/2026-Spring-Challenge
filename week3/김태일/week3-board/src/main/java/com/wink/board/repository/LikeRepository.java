package com.wink.board.repository;

import com.wink.board.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // 이미 눌렀는지 확인 → SELECT ... WHERE user_id = ? AND post_id = ?
    boolean existsByUserIdAndPostId(Long userId, Long postId);

    // 취소할 좋아요 찾기
    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);

    // 게시글의 좋아요 개수
    long countByPostId(Long postId);

    // 게시글 삭제 전에 그 글의 좋아요 전부 삭제 (호출하는 쪽에 @Transactional 필요)
    void deleteByPostId(Long postId);
}
