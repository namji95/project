package com.namji.todolist.entity;

import com.namji.todolist.dto.request.TodoRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Todo extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long todoId;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String writeDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  public Todo(User user, String title, String content, String password, String now) {
    this.user = user;
    this.title = title;
    this.content = content;
    this.password = password;
    this.writeDate = now;
  }

  public void update(TodoRequest request) {
    this.title = request.getTitle();
    this.content = request.getContent();
  }
}
