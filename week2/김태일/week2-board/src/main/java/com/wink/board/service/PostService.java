package com.wink.board.service;

import com.wink.board.domain.Post;
import com.wink.board.dto.*;
import com.wink.board.exception.PostNotFoundException;
import com.wink.board.repository.MemoryPostRepository;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;

@Service
public class PostService {

    private final MemoryPostRepository postRepository;

    public PostService(MemoryPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponse create(PostCreateRequest request) {
        if (request.title() == null || request.title().isBlank()) {
            throw new IllegalArgumentException("제목은 비어 있을 수 없습니다.");
        }
        Post post = new Post(
                postRepository.nextId(),
                request.title(),
                request.content(),
                request.writer()
        );
        postRepository.save(post);
        return PostResponse.from(post);
    }

    // 전체 조회 + 작성자 검색 + 페이징
    public PageResponse<PostResponse> findAll(String writer, int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("page는 0 이상이어야 합니다.");
        }
        if (size < 1 || size > 100) {
            throw new IllegalArgumentException("size는 1~100 사이여야 합니다.");
        }

        List<Post> posts = (writer == null || writer.isBlank())
                ? postRepository.findAll()
                : postRepository.findByWriter(writer);

        List<PostResponse> sorted = posts.stream()
                .sorted(Comparator.comparing(Post::getId).reversed())   // 최신 글이 먼저
                .map(PostResponse::from)
                .toList();

        return PageResponse.of(sorted, page, size);
    }

    public PostResponse findById(Long id) {
        return PostResponse.from(getPost(id));
    }

    public PostResponse update(Long id, PostUpdateRequest request) {
        Post post = getPost(id);
        post.update(request.title(), request.content());
        return PostResponse.from(post);
    }

    public void delete(Long id) {
        getPost(id);
        postRepository.deleteById(id);
    }

    public PostResponse like(Long id) {
        Post post = getPost(id);
        post.like();
        return PostResponse.from(post);
    }

    private Post getPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }
}
