package com.wink.board.service;

import com.wink.board.domain.Post;
import com.wink.board.exception.InvalidPostException;
import com.wink.board.exception.PostNotFoundException;
import com.wink.board.repository.PostRepository;
import java.util.List;

public class PostService {

    private final PostRepository postRepository;
    private Long sequence = 0L;   // id 자동 증가용

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post create(String title, String content, String writer, Boolean isNotice) {
        if (title == null || title.isBlank()) {
            throw new InvalidPostException("제목은 비어 있을 수 없습니다.");
        }
        if (title.length() > 20){
            throw new InvalidPostException("제목은 20자 이하여야 합니다.");
        }
        Post post = new Post(++sequence, title, content, writer, isNotice);
        return postRepository.save(post);
    }

    public List<Post> findByTitle(String title) {
        List<Post> posts = findAll();

        return posts.stream()
                .filter(post -> post.getTitle().contains(title)).toList();
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
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
}