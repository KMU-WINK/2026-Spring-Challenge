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

    // 제목 검증은 Post가 스스로 한다 (생성자, update 모두)
    public Post create(String title, String content, String writer) {
        Post post = new Post(sequence + 1, title, content, writer);
        sequence++;   // 검증을 통과해 객체가 만들어진 뒤에만 번호를 올린다
        return postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    // 저장소에 검색 메서드를 추가하지 않고 서비스에서 거른다
    // → 저장소를 갈아끼워도 검색 기능은 그대로 동작
    public List<Post> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new InvalidPostException("검색어를 입력해주세요.");
        }
        return findAll().stream()
                .filter(post -> post.getTitle().contains(keyword))
                .toList();
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
