package com.namji.todolist.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TodoResponse {
  private Long id;
  private String title;
  private String content;
  private String writeDate;
}
