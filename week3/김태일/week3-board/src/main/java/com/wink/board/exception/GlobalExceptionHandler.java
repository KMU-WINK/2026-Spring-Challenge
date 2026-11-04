package com.wink.board.exception;

import com.wink.board.dto.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(PostNotFoundException.class)
  public ResponseEntity<ApiResponse<Void>> handleNotFound(PostNotFoundException e) {
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)   // 404
            .body(ApiResponse.fail(e.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException e) {
    String message = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
    return ResponseEntity
            .badRequest()                    // 400
            .body(ApiResponse.fail(message));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException e) {
    return ResponseEntity.badRequest()
            .body(Map.of("message", e.getMessage()));
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleUserNotFound(UserNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Map.of("message", e.getMessage()));
  }

  @ExceptionHandler(DuplicateNicknameException.class)
  public ResponseEntity<Map<String, String>> handleDuplicate(DuplicateNicknameException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(Map.of("message", e.getMessage()));
  }

  @ExceptionHandler(DuplicateLikeException.class)
  public ResponseEntity<Map<String, String>> handleDuplicateLike(DuplicateLikeException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT)      // 409
            .body(Map.of("message", e.getMessage()));
  }

  @ExceptionHandler(LikeNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleLikeNotFound(LikeNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)     // 404
            .body(Map.of("message", e.getMessage()));
  }

  // 동시에 두 번 눌러서 exists 확인을 둘 다 통과한 경우 → DB 유니크 제약이 막고 이 예외가 남
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String, String>> handleDataIntegrity(DataIntegrityViolationException e) {
    return ResponseEntity.status(HttpStatus.CONFLICT)      // 409
            .body(Map.of("message", "이미 처리된 요청이거나 중복된 데이터입니다."));
  }
}
