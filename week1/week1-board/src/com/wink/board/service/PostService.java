package com.wink.board.service;

import com.wink.board.domain.Post;
import com.wink.board.repository.PostRepository;
import java.util.List;

public class PostService {
    private final PostRepository postRepository;
    private Long sequence = 0L;
    private static final int MaxTitleLength = 20;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post create(String title, String content, String writer) {
        // == 은 주소가 같은지 판단,문자를 비교하려면 equals()사용
        if(title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목은 비어 있을 수 없습니다.");
        }
        if(title.length() > MaxTitleLength) {
            throw new IllegalArgumentException("제목은 " + MaxTitleLength +  "자를 넘을 수 없습니다.");
        }
        Post post = new Post(++sequence, title, content, writer);
        return postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입ㄴ디ㅏ. id=" + id));
    }

    public Post update(Long id, String title, String content) {
        if(title.length() > MaxTitleLength) {
            throw new IllegalArgumentException("제목은 " + MaxTitleLength +  "자를 넘을 수 없습니다.");
        }
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

}
