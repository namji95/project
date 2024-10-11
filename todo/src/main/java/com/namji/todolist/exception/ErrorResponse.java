package com.namji.todolist.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorResponse {
  private int status;
  private String name;
  private String messgae;

  public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
    return ResponseEntity
        .status(errorCode.getHttpStatus())
        .body(ErrorResponse.builder()
            .status(errorCode.getHttpStatus().value())
            .name(errorCode.name())
            .messgae(errorCode.getMessage())
            .build());
  }
}
