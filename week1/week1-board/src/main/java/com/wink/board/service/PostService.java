package com.wink.board.service;

import com.wink.board.domain.Post;
import com.wink.board.repository.PostRepository;
import java.util.List;

public class PostService {

    private final PostRepository postRepository;
    private Long sequence = 0L; //sequence : 게시글 번호 생성기

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    //게시글 작성기능
    public Post create(String title, String content, String writer) {
        //제목 예외 처리
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목은 비어 있을 수 없습니다.");
        }
        //제목 문제 없는 경우 저장소로 넘겨서 게시물 저장
        Post post = new Post(++sequence, title, content, writer);
        return postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        " 존재하지 않는 게시글입니다. id=" + id
                ));
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

    public Post like (Long id){
        Post post = findById(id);
        post.like();
        return post;
    }
}
