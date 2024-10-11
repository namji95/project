package com.namji.todolist.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  SUCCESS(HttpStatus.OK, "OK");

  private final HttpStatus httpStatus;
  private final String message;
}
