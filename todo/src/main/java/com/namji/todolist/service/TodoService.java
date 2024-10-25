package com.namji.todolist.service;

import com.namji.todolist.dto.request.TodoRequest;
import com.namji.todolist.dto.response.TodoResponse;
import com.namji.todolist.entity.Todo;
import com.namji.todolist.exception.CustomException;
import com.namji.todolist.exception.ErrorCode;
import com.namji.todolist.repository.TodoRepository;
import com.namji.todolist.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TodoService {

  private final TodoRepository todoRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public TodoResponse createTodo(UserDetailsImpl userDetails, TodoRequest todoRequest) {
    String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
    String password = passwordEncoder.encode(todoRequest.getPassword());
    Todo todo = new Todo(
        userDetails.getUser(),
        todoRequest.getTitle(),
        todoRequest.getContent(),
        password,
        formatDate);

    todoRepository.save(todo);

    return new TodoResponse(
        todo.getUser().getUserId(),
        todo.getTitle(),
        todo.getContent(),
        todo.getWriteDate());
  }

  @Transactional(readOnly = true)
  public TodoResponse getTodo(Long id) {
    Todo findTodo = findTodo(id);

    return new TodoResponse(
        findTodo.getTodoId(),
        findTodo.getTitle(),
        findTodo.getContent(),
        findTodo.getWriteDate());
  }

  @Transactional(readOnly = true)
  public List<TodoResponse> getAllMyTodo(UserDetailsImpl userDetails) {
    List<TodoResponse> responses = new ArrayList<>();
    List<Todo> findAllTodo = todoRepository.findAllByUser(userDetails.getUser());

    if (findAllTodo.isEmpty()) {
      throw new CustomException(ErrorCode.NOT_FOUND_TODO);
    }

    for (Todo todo : findAllTodo) {
      responses.add(
          new TodoResponse(
              todo.getTodoId(),
              todo.getTitle(),
              todo.getContent(),
              todo.getWriteDate()));
    }

    return responses;
  }

  @Transactional(readOnly = true)
  public List<TodoResponse> getAllTodo() {
    List<Todo> findAllTodo = todoRepository.findAllByOrderByWriteDateDesc();
    List<TodoResponse> responses = new ArrayList<>();

    if (findAllTodo.isEmpty()) {
      throw new CustomException(ErrorCode.NOT_FOUND_TODO);
    }

    for (Todo todo : findAllTodo) {
      responses.add(
          new TodoResponse(
              todo.getTodoId(),
              todo.getTitle(),
              todo.getContent(),
              todo.getWriteDate()));
    }

    return responses;
  }

  @Transactional
  public TodoResponse updateTodo(UserDetailsImpl userDetails, TodoRequest todoRequest, Long id) {
    Todo updateTodo = findTodo(id);

    if (!Objects.equals(userDetails.getUser().getUserId(), updateTodo.getUser().getUserId())) {
      throw new CustomException(ErrorCode.USER_CHECK);
    }
    if (!passwordEncoder.matches(todoRequest.getPassword(), updateTodo.getPassword())) {
      throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
    }

    updateTodo.update(todoRequest);

    Todo findTodo = findTodo(id);

    return new TodoResponse(
        findTodo.getTodoId(),
        findTodo.getTitle(),
        findTodo.getContent(),
        findTodo.getWriteDate());
  }

  @Transactional
  public String deleteTodo(UserDetailsImpl userDetails, TodoRequest todoRequest, Long id) {
    Todo deleteTodo = findTodo(id);

    if (!Objects.equals(userDetails.getUser().getUserId(), deleteTodo.getUser().getUserId())) {
      throw new CustomException(ErrorCode.USER_CHECK);
    }
    if (!passwordEncoder.matches(todoRequest.getPassword(), deleteTodo.getPassword())) {
      throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
    }

    todoRepository.deleteById(id);

    return "게시글이 삭제되었습니다.";
  }

  private Todo findTodo(Long id) {
    return todoRepository.findById(id).orElseThrow(
        () -> new CustomException(ErrorCode.NOT_FOUND_TODO));
  }
}