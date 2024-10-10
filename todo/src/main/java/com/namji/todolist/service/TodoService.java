package com.namji.todolist.service;

import com.namji.todolist.dto.request.TodoRequest;
import com.namji.todolist.dto.response.TodoResponse;
import com.namji.todolist.entity.Todo;
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

  @Transactional(readOnly = true)
  public TodoResponse getTodo(Long id) {
    Optional<Todo> findTodo = todoRepository.findById(id);

    if (findTodo.isEmpty()) {
      throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
    }

    return new TodoResponse(
        findTodo.get().getTodoId(),
        findTodo.get().getTitle(),
        findTodo.get().getContent());
  }

  @Transactional(readOnly = true)
  public List<TodoResponse> getAllTodo() {
    List<Todo> findAllTodo = todoRepository.findAll();
    List<TodoResponse> responses = new ArrayList<>();

    if (findAllTodo.isEmpty()) {
      throw new IllegalArgumentException("일정이 존재하지 않습니다.");
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
    Todo updateTodo = todoRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

    if (!Objects.equals(userDetails.getUser().getUserId(), updateTodo.getUser().getUserId())) {
      throw new IllegalArgumentException("해당 게시글 작성자만 수정할 수 있습니다.");
    }

    updateTodo.update(todoRequest);

    Todo findTodo = todoRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

    return new TodoResponse(
        findTodo.getTodoId(),
        findTodo.getTitle(),
        findTodo.getContent());
  }

  @Transactional
  public String deleteTodo(UserDetailsImpl userDetails, Long id) {
    Todo findTodo = todoRepository.findById(id).orElseThrow(
        () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

    if (!Objects.equals(userDetails.getUser().getUserId(), findTodo.getUser().getUserId())) {
      throw new IllegalArgumentException("해당 게시글 작성자만 삭제할 수 있습니다.");
    }

    todoRepository.deleteById(id);

    return "게시글이 삭제되었습니다.";
  }
}
