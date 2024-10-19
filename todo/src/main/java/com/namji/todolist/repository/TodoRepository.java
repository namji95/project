package com.namji.todolist.repository;

import com.namji.todolist.entity.Todo;
import com.namji.todolist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

  List<Todo> findAllByOrderByWriteDateDesc();

  List<Todo> findAllByUser(User user);

}
