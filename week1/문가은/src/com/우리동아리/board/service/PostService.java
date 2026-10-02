package com.우리동아리.board.service;

import com.우리동아리.board.domain.Post;
import com.우리동아리.board.repository.PostRepository;
import java.util.List;

public class PostService {

    private final PostRepository postRepository;
    private Long sequence = 0L;   // id 자동 증가용

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post create(String title, String content, String writer) {
        validateTitle(title);
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목은 비어 있을 수 없습니다.");
        }
        Post post = new Post(++sequence, title, content, writer);
        return postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "존재하지 않는 게시글입니다. id=" + id));
    }

    public Post update(Long id, String title, String content) {
        Post post = findById(id);
        post.update(title, content);
        return post;
    }

    public void delete(Long id) {
        findById(id);              // 없으면 여기서 예외
        postRepository.deleteById(id);
    }

    public Post like(Long id) {
        Post post = findById(id);
        post.like();
        return post;
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