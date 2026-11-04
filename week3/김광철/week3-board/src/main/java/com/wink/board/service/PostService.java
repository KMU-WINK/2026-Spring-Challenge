package com.wink.board.service;

import com.wink.board.domain.Like;
import com.wink.board.domain.Post;
import com.wink.board.domain.User;
import com.wink.board.dto.*;
import com.wink.board.exception.DuplicateLikeException;
import com.wink.board.exception.PostNotFoundException;
import com.wink.board.exception.UserNotFoundException;
import com.wink.board.repository.LikeRepository;
import com.wink.board.repository.PostRepository;
import com.wink.board.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
    }

    @Transactional
    public PostResponse create(PostCreateRequest request) {
        User user = getUser(request.userId());

        Post post = new Post(request.title(), request.content(), user);
        postRepository.save(post);          // id는 저장 시 DB가 부여
        return PostResponse.from(post);
    }

    public List<PostResponse> findAll(PostSearchRequest request) {
        PageRequest page = PageRequest.of(request.page(), request.size());
        List<Post> posts = request.writer() == null
                ? postRepository.findAll(page).getContent()
                : postRepository.findByUserNickname(request.writer(), page);
        return posts.stream()
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
        postRepository.flush();
        return PostResponse.from(post);
    }

    @Transactional
    public void delete(Long id) {
        Post post = getPostForUpdate(id);
        likeRepository.deleteForPost(id);
        postRepository.delete(post);
    }

    @Transactional
    public void addLike(Long postId, Long userId) {
        Post post = getPostForUpdate(postId);
        User user = getUser(userId);
        if (likeRepository.existsByPostIdAndUserId(postId, userId)) {
            throw new DuplicateLikeException();
        }
        likeRepository.save(new Like(post, user));
    }

    @Transactional
    public void removeLike(Long postId, Long userId) {
        getPostForUpdate(postId);
        getUser(userId);
        likeRepository.deleteForUser(postId, userId);
    }

    private Post getPostForUpdate(Long id) {
        return postRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private Post getPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }
}
