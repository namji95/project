package com.namji.todolist.service;

import com.namji.todolist.dto.request.TodoRequest;
import com.namji.todolist.dto.response.TodoResponse;
import com.namji.todolist.entity.Todo;
import com.namji.todolist.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {

  private final TodoRepository todoRepository;

  public TodoResponse todoCreate(TodoRequest todoRequest) {
    Todo findTodo = todoRepository.findByTitle(todoRequest.getTitle());

    if (findTodo != null) {
      throw new IllegalArgumentException("이미 존재하는 일정입니다.");
    }

    Todo todo = new Todo(
        todoRequest.getTitle(),
        todoRequest.getContent());

    todoRepository.save(todo);

    return new TodoResponse(
        todo.getTitle(),
        todo.getContent());
  }
}
