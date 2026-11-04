package com.wink.board.service;

import com.wink.board.domain.Post;
import com.wink.board.domain.User;
import com.wink.board.dto.*;
import com.wink.board.exception.PostNotFoundException;
import com.wink.board.exception.UserNotFoundException;
import com.wink.board.repository.LikeRepository;
import com.wink.board.repository.PostRepository;
import com.wink.board.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    public PostService(PostRepository postRepository,
                       UserRepository userRepository,
                       LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
    }

    @Transactional
    public PostResponse create(PostCreateRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException(request.userId()));

        Post post = new Post(request.title(), request.content(), user);
        postRepository.save(post);          // id는 저장 시 DB가 부여
        return PostResponse.from(post);
    }

    public List<PostResponse> findAll() {
        return postRepository.findAllWithUser().stream()   // findAll() → findAllWithUser()
                .map(PostResponse::from)
                .toList();
    }

    public PostResponse findById(Long id) {
        return PostResponse.from(getPost(id));
    }

    @Transactional
    public PostResponse update(Long id, PostUpdateRequest request) {
        Post post = getPost(id);
        post.update(request.title(), request.content());
        return PostResponse.from(post);     // save() 없음 — 더티 체킹
    }

    @Transactional
    public void delete(Long id) {
        Post post = getPost(id);
        likeRepository.deleteByPostId(id);  // 좋아요가 게시글을 외래키로 가리키고 있으니 먼저 삭제
        postRepository.delete(post);
    }

    private Post getPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }
}