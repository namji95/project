package com.namji.todolist.service;

import com.namji.todolist.dto.Request;
import com.namji.todolist.dto.Response;
import com.namji.todolist.entity.Todo;
import com.namji.todolist.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {

  private final TodoRepository todoRepository;

  public Response todoCreate(Request request) {
    Todo findTodo = todoRepository.findByTitle(request.getTitle());

    if (findTodo != null) {
      throw new IllegalArgumentException("이미 존재하는 일정입니다.");
    }

    Todo todo = new Todo(
        request.getTitle(),
        request.getContent());

    todoRepository.save(todo);

    return new Response(
        todo.getTitle(),
        todo.getContent());
  }
}
