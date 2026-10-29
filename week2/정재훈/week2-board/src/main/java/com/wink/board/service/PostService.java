package com.wink.board.service;

import com.wink.board.domain.Post;
import com.wink.board.dto.*;
import com.wink.board.repository.MemoryPostRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostService {
    private final MemoryPostRepository postRepository;

    public PostService(MemoryPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponse create(PostCreateRequest request) {
        validateTitle(request.title());
        Post post = new Post(postRepository.nextId(), request.title(), request.content(), request.writer());
        return PostResponse.from(postRepository.save(post));
    }

    public List<PostResponse> findAll() {
        return postRepository.findAll().stream().map(PostResponse::from).toList();
    }

    public PostResponse findById(Long id) { return PostResponse.from(getPost(id)); }

    public PostResponse update(Long id, PostUpdateRequest request) {
        Post post = getPost(id);
        validateTitle(request.title());
        post.update(request.title(), request.content());
        return PostResponse.from(post);
    }

    public void delete(Long id) {
        getPost(id);
        postRepository.deleteById(id);
    }

    public PostResponse like(Long id) {
        Post post = getPost(id);
        synchronized (post) {
            post.like();
            return PostResponse.from(post);
        }
    }

    private Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 게시글입니다. id=" + id));
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목은 비어 있을 수 없습니다.");
        }
        if (title.length() > 20) {
            throw new IllegalArgumentException("제목은 20자 이하여야 합니다.");
        }
    }
}
