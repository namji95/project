package com.namji.todolist.service;

import com.namji.todolist.dto.request.TodoRequest;
import com.namji.todolist.dto.response.TodoResponse;
import com.namji.todolist.entity.Todo;
import com.namji.todolist.exception.CustomException;
import com.namji.todolist.exception.ErrorCode;
import com.namji.todolist.repository.TodoRepository;
import com.namji.todolist.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {

  private final TodoRepository todoRepository;

  @Transactional
  public TodoResponse createTodo(UserDetailsImpl userDetails, TodoRequest todoRequest) {
    Optional<Todo> findTodo = todoRepository.findByTitle(todoRequest.getTitle());

    if (findTodo.isPresent()) {
      throw new CustomException(ErrorCode.DUPLICATE_TODO);
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

  @Transactional(readOnly = true)
  public TodoResponse getTodo(Long id) {
    Todo findTodo = findTodo(id);

    return new TodoResponse(
        findTodo.getTodoId(),
        findTodo.getTitle(),
        findTodo.getContent());
  }

  @Transactional(readOnly = true)
  public List<TodoResponse> getAllTodo() {
    List<Todo> findAllTodo = todoRepository.findAll();
    List<TodoResponse> responses = new ArrayList<>();

    if (findAllTodo.isEmpty()) {
      throw new CustomException(ErrorCode.NOT_FOUND_TODO);
    }

    for (Todo todo : findAllTodo) {
      responses.add(
          new TodoResponse(
              todo.getTodoId(),
              todo.getTitle(),
              todo.getContent()
          )
      );
    }

    return responses;
  }

  @Transactional
  public TodoResponse updateTodo(UserDetailsImpl userDetails, TodoRequest todoRequest, Long id) {
    Todo updateTodo = findTodo(id);

    if (!Objects.equals(userDetails.getUser().getUserId(), updateTodo.getUser().getUserId())) {
      throw new CustomException(ErrorCode.USER_CHECK);
    }

    updateTodo.update(todoRequest);

    Todo findTodo = findTodo(id);

    return new TodoResponse(
        findTodo.getTodoId(),
        findTodo.getTitle(),
        findTodo.getContent());
  }

  @Transactional
  public String deleteTodo(UserDetailsImpl userDetails, Long id) {
    Todo findTodo = findTodo(id);

    if (!Objects.equals(userDetails.getUser().getUserId(), findTodo.getUser().getUserId())) {
      throw new CustomException(ErrorCode.USER_CHECK);
    }

    todoRepository.deleteById(id);

    return "게시글이 삭제되었습니다.";
  }

  private Todo findTodo(Long id) {
    return todoRepository.findById(id).orElseThrow(
        () -> new CustomException(ErrorCode.NOT_FOUND_TODO));
  }
}
