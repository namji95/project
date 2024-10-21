package com.namji.todolist.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  SUCCESS(HttpStatus.OK, "OK"),

  NOT_FOUND_TODO(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),
  DUPLICATE_TODO(HttpStatus.BAD_REQUEST, "이미 존재하는 일정입니다."),
  DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
  USER_CHECK(HttpStatus.BAD_REQUEST, "해당 게시글 작성자만 수정 및 삭제할 수 있습니다."),
  PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "일치하지 않는 비밀번호입니다.");

  private final HttpStatus httpStatus;
  private final String message;
}
