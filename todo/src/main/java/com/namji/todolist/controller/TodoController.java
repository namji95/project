package com.namji.todolist.controller;

import com.namji.todolist.dto.request.TodoRequest;
import com.namji.todolist.dto.response.TodoResponse;
import com.namji.todolist.security.UserDetailsImpl;
import com.namji.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/todo")
public class TodoController {

  private final TodoService todoService;

  @PostMapping("/create")
  public TodoResponse todoCreate (
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody TodoRequest todoRequest) {
    TodoResponse todoResponse = todoService.todoCreate(userDetails, todoRequest);

    return todoResponse;
  }
}
