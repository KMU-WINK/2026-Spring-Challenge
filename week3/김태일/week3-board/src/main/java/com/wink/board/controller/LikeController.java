package com.wink.board.controller;

import com.wink.board.dto.ApiResponse;
import com.wink.board.dto.LikeRequest;
import com.wink.board.dto.LikeResponse;
import com.wink.board.service.LikeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    // 좋아요 추가 → POST /api/posts/1/likes  body: {"userId": 1}
    @PostMapping
    public ResponseEntity<ApiResponse<LikeResponse>> like(
            @PathVariable Long postId,
            @Valid @RequestBody LikeRequest request
    ) {
        LikeResponse response = likeService.like(postId, request.userId());
        return ResponseEntity.status(HttpStatus.CREATED)   // 201
                .body(ApiResponse.success(response, "좋아요를 눌렀습니다."));
    }

    // 좋아요 취소 → DELETE /api/posts/1/likes?userId=1
    // DELETE 요청은 바디를 안 쓰는 게 관례라서 userId를 쿼리 파라미터로 받음
    @DeleteMapping
    public ResponseEntity<Void> unlike(
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {
        likeService.unlike(postId, userId);
        return ResponseEntity.noContent().build();         // 204
    }
}
