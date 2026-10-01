package com.wink.board.service;

import com.wink.board.domain.Post;
import com.wink.board.repository.PostRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;
import com.wink.board.domain.Notice;

public class PostService {
    private final PostRepository postRepository;
    private Long sequence = 0L;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public Post create(String title, String content, String writer) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목은 비어 있을 수 없습니다.");
        }
        if (title.length() > 20){
            throw new IllegalArgumentException("제목은 20자를 넘길 수 없습니다.");
        }
        Post post = new Post(++sequence, title, content, writer);
        return postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll().stream().sorted(Comparator.comparing((Post p) -> !p.isNotice()).thenComparing(Post::getId)).toList();
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
        findById(id);
        postRepository.deleteById(id);
    }

    public Post like(Long id) {
        Post post = findById(id);
        post.like();
        return post;
    }

    public List<Post> searchByTitle(String keyword) {
        if (keyword == null || keyword.isBlank()){
            throw new IllegalArgumentException("검색어는 비어 있을 수 없습니다.");
        }
        return postRepository.findAll().stream().filter(post -> post.getTitle().contains(keyword)).collect(Collectors.toList());
    }

    public Post createNotice(String title, String content, String writer) {
        Post notice = new Notice(++sequence, title, content, writer);
        return postRepository.save(notice);
    }

}
