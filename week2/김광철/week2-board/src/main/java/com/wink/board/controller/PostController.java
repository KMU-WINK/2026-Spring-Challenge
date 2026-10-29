package com.wink.board.controller;

import com.wink.board.dto.*;
import com.wink.board.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/posts")   // 이 컨트롤러의 공통 경로
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 작성 → POST /api/posts
    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> create(@Valid @RequestBody PostCreateRequest request) {
        PostResponse post = postService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(post));  // 201
    }

    // 전체 조회 → GET /api/posts
    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse>>> findAll(@Valid @ModelAttribute PostSearchRequest postSearchRequest) {
        List<PostResponse> posts = postService.findAll(postSearchRequest);
        return ResponseEntity.ok(ApiResponse.success(posts));  // 200
    }

    // 상세 조회 → GET /api/posts/1
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> findById(@PathVariable Long id) {
        PostResponse post = postService.findById(id);

        return ResponseEntity.ok(ApiResponse.success(post));
    }

    // 수정 → PATCH /api/posts/1
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody PostUpdateRequest request
    ) {
        PostResponse post = postService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success(post));
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