package com.wink.board.controller;

import com.wink.board.dto.*;
import com.wink.board.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")   // 이 컨트롤러의 공통 경로
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 작성 → POST /api/posts
    @PostMapping
    public ResponseEntity<PostResponse> create(@Valid @RequestBody PostCreateRequest request) {
        PostResponse response = postService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);  // 201
    }

    // 전체 조회 → GET /api/posts
    @GetMapping
    public ResponseEntity<List<PostResponse>> findAll() {
        return ResponseEntity.ok(postService.findAll());  // 200
    }

    // 상세 조회 → GET /api/posts/1
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    // 수정 → PATCH /api/posts/1
    @PatchMapping("/{id}")
    public ResponseEntity<PostResponse> update(
            @PathVariable Long id,
            @RequestBody PostUpdateRequest request
    ) {
        return ResponseEntity.ok(postService.update(id, request));
    }

    // 삭제 → DELETE /api/posts/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();  // 204
    }

    // 좋아요 → POST /api/posts/1/likes
    @PostMapping("/{id}/likes")
    public ResponseEntity<PostResponse> like(@PathVariable Long id) {
        return ResponseEntity.ok(postService.like(id));
    }
}