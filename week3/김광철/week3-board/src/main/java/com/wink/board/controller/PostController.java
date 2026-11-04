package com.wink.board.controller;

import com.wink.board.dto.*;
import com.wink.board.service.PostService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Tag(name = "게시글", description = "게시글 API")
@RequestMapping("/api/posts")   // 이 컨트롤러의 공통 경로
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 작성 → POST /api/posts
    @Operation(summary = "게시글 작성")
    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> create(@Valid @RequestBody PostCreateRequest request) {
        PostResponse post = postService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(post));  // 201
    }

    // 전체 조회 → GET /api/posts
    @Operation(summary = "게시글 목록 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse>>> findAll(@Valid @ModelAttribute PostSearchRequest postSearchRequest) {
        List<PostResponse> posts = postService.findAll(postSearchRequest);
        return ResponseEntity.ok(ApiResponse.success(posts));  // 200
    }

    // 상세 조회 → GET /api/posts/1
    @Operation(summary = "게시글 상세 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> findById(@PathVariable Long id) {
        PostResponse post = postService.findById(id);

        return ResponseEntity.ok(ApiResponse.success(post));
    }

    // 수정 → PATCH /api/posts/1
    @Operation(summary = "게시글 부분 수정")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody PostUpdateRequest request
    ) {
        PostResponse post = postService.update(id, request);
        return ResponseEntity.ok(ApiResponse.success(post));
    }

    // 삭제 → DELETE /api/posts/1
    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();  // 204
    }

    @Operation(summary = "좋아요 추가", description = "같은 유저가 이미 좋아요를 눌렀으면 409를 반환합니다.")
    @PostMapping("/{id}/likes")
    public ResponseEntity<ApiResponse<Void>> addLike(@PathVariable Long id, @RequestParam Long userId) {
        postService.addLike(id, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(null));
    }

    @Operation(summary = "좋아요 취소", description = "이미 취소한 경우에도 204를 반환합니다.")
    @DeleteMapping("/{id}/likes")
    public ResponseEntity<Void> removeLike(@PathVariable Long id, @RequestParam Long userId) {
        postService.removeLike(id, userId);
        return ResponseEntity.noContent().build();
    }
}
