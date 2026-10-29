package com.wink.board.controller;

import com.wink.board.dto.*;
import com.wink.board.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        PostResponse response = postService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)  // 201
                .body(ApiResponse.success(response, "게시글이 작성되었습니다."));
    }

    // 전체 조회 → GET /api/posts?page=0&size=10
    // 작성자 검색 → GET /api/posts?writer=익명1&page=0&size=10
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<PostResponse>>> findAll(
            @RequestParam(required = false) String writer,      // 없으면 null → 전체 조회
            @RequestParam(defaultValue = "0") int page,         // 없으면 0페이지
            @RequestParam(defaultValue = "20") int size         // 없으면 20개씩
    ) {
        return ResponseEntity.ok(  // 200
                ApiResponse.success(postService.findAll(writer, page, size), "게시글 목록 조회 성공"));
    }

    // 상세 조회 → GET /api/posts/1
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success(postService.findById(id), "게시글 조회 성공"));
    }

    // 수정 → PATCH /api/posts/1
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody PostUpdateRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(postService.update(id, request), "게시글이 수정되었습니다."));
    }

    // 삭제 → DELETE /api/posts/1
    // 204 No Content는 규칙상 바디를 가질 수 없어서 공통 포맷 없이 상태 코드만 반환
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();  // 204
    }

    // 좋아요 → POST /api/posts/1/likes
    @PostMapping("/{id}/likes")
    public ResponseEntity<ApiResponse<PostResponse>> like(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success(postService.like(id), "좋아요를 눌렀습니다."));
    }
}
