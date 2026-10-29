package com.wink.board.exception;

import com.wink.board.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(PostNotFoundException.class)
  public ResponseEntity<ApiResponse<Void>> handleNotFound(PostNotFoundException e) {
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)   // 404
            .body(ApiResponse.fail(e.getMessage()));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiResponse<Void>> handleBadRequest(IllegalArgumentException e) {
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)  // 400
            .body(ApiResponse.fail(e.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException e) {
    String message = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
    return ResponseEntity
            .badRequest()                    // 400
            .body(ApiResponse.fail(message));
  }
}
