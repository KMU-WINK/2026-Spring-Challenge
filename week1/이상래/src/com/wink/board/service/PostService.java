package com.wink.board.service;

import com.wink.board.domain.Post;
import com.wink.board.exception.PostNotFoundException;
import com.wink.board.repository.PostRepository;

import java.util.List;

public class PostService {

    private final PostRepository postRepository;
    private Long sequence = 0L;   // id 자동 증가용

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 제목 검증은 Post가 스스로 한다 (생성자, update 모두)
    public Post create(String title, String content, String writer) {
        Post post = new Post(sequence + 1, title, content, writer);
        sequence++;   // 검증을 통과해 객체가 만들어진 뒤에만 번호를 올린다
        return postRepository.save(post);
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
