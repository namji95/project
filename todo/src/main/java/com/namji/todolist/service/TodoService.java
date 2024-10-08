package com.namji.todolist.service;

import com.namji.todolist.dto.request.TodoRequest;
import com.namji.todolist.dto.response.TodoResponse;
import com.namji.todolist.entity.Todo;
import com.namji.todolist.repository.TodoRepository;
import com.namji.todolist.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {

  private final TodoRepository todoRepository;

  public TodoResponse todoCreate(UserDetailsImpl userDetails, TodoRequest todoRequest) {
    Optional<Todo> findTodo = todoRepository.findByTitle(todoRequest.getTitle());

    if (findTodo.isPresent()) {
      throw new IllegalArgumentException("이미 존재하는 일정입니다.");
    }

    Todo todo = new Todo(
        userDetails.getUser(),
        todoRequest.getTitle(),
        todoRequest.getContent());

    todoRepository.save(todo);

    return new TodoResponse(
        todo.getUser().getUserId(),
        todo.getTitle(),
        todo.getContent());
  }
}
