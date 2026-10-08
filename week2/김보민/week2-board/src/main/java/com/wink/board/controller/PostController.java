package com.wink.board.controller;

import com.wink.board.dto.*;
import com.wink.board.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> create(@Valid @RequestBody PostCreateRequest request) {
        PostResponse response = postService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(response, "게시글이 작성되었습니다."));
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok(postService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(postService.findById(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> update(
            @PathVariable Long id,
            @RequestBody PostUpdateRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.ok(postService.update(id, request), "게시글이 수정되었습니다."));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/likes")
    public ResponseEntity<ApiResponse<PostResponse>> like(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(postService.like(id)));
    }
}