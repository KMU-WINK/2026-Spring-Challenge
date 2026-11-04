package com.wink.board.service;

import com.wink.board.domain.Like;
import com.wink.board.domain.Post;
import com.wink.board.domain.User;
import com.wink.board.dto.LikeResponse;
import com.wink.board.exception.DuplicateLikeException;
import com.wink.board.exception.LikeNotFoundException;
import com.wink.board.exception.PostNotFoundException;
import com.wink.board.exception.UserNotFoundException;
import com.wink.board.repository.LikeRepository;
import com.wink.board.repository.PostRepository;
import com.wink.board.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public LikeService(LikeRepository likeRepository,
                       UserRepository userRepository,
                       PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    // 좋아요 추가: 유저 확인(404) → 게시글 확인(404) → 중복 확인(409) → 저장
    @Transactional
    public LikeResponse like(Long postId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        if (likeRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new DuplicateLikeException(userId, postId);
        }

        likeRepository.save(new Like(user, post));
        return new LikeResponse(postId, userId, likeRepository.countByPostId(postId));
    }

    // 좋아요 취소: 누른 기록 찾기(없으면 404) → 삭제
    @Transactional
    public void unlike(Long postId, Long userId) {
        Like like = likeRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(() -> new LikeNotFoundException(userId, postId));
        likeRepository.delete(like);
    }
}
