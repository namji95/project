package com.namji.todolist.controller;

import com.namji.todolist.dto.Request;
import com.namji.todolist.dto.Response;
import com.namji.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
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
  public Response todoCreate (@RequestBody Request request) {
    Response response = todoService.todoCreate(request);

    return response;
  }
}
