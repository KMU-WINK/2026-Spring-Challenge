package com.wink.board.service;

import com.wink.board.domain.Post;
import com.wink.board.domain.Notice;
import com.wink.board.exception.InvalidPostException;
import com.wink.board.repository.PostRepository;
import java.util.List;

public class PostService {

    private final PostRepository postRepository;
    private Long sequence = 0L;   // id 자동 증가용

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post create(String title, String content, String writer) {
        if (title == null || title.isBlank()) {
            throw new InvalidPostException("제목은 비어 있을 수 없습니다.");
        } else if (title.length() > 20){
            throw new InvalidPostException("제목의 최대 길이는 20자입니다.");
        }
        Post post = new Post(++sequence, title, content, writer);
        return postRepository.save(post);
    }

    public Post createNotice(String title, String content, String writer) {
        if (title == null || title.isBlank()) {
            throw new InvalidPostException("제목은 비어 있을 수 없습니다.");
        } else if (title.length() > 20){
            throw new InvalidPostException("제목의 최대 길이는 20자입니다.");
        }

        Notice notice = new Notice(++sequence, title, content, writer);
        return postRepository.save(notice);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public List<Post> findByTitle(String keyword) {
        return postRepository.findByTitle(keyword);
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
}
