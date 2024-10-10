package com.namji.todolist.controller;

import com.namji.todolist.dto.request.TodoRequest;
import com.namji.todolist.dto.response.TodoResponse;
import com.namji.todolist.security.UserDetailsImpl;
import com.namji.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/todo")
public class TodoController {

  private final TodoService todoService;

  @PostMapping("/create")
  public TodoResponse createTodo(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody TodoRequest todoRequest) {

    return todoService.createTodo(userDetails, todoRequest);
  }

  @GetMapping("/get/{id}")
  public TodoResponse getTodo(@PathVariable Long id) {
    return todoService.getTodo(id);
  }

  @GetMapping("/get-all")
  public List<TodoResponse> getAllTodo() {
    return todoService.getAllTodo();
  }

  @PutMapping("/update/{id}")
  public TodoResponse updateTodo(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody TodoRequest todoRequest,
      @PathVariable Long id) {
    return todoService.updateTodo(userDetails, todoRequest, id);
  }

  @DeleteMapping("/delete/{id}")
  public String deleteTodo(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable Long id
  ) {
    return todoService.deleteTodo(userDetails, id);
  }
}
