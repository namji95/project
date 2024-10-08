package com.namji.todolist.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  private String email;
  private String username;
  private String password;

  /**
   * mapped = "user" => Post 엔티티에서 user 필드에 의해 이 관계가 관리된다는 의미입니다.
   * cascade = CascadeType.ALL: 유저가 변경될 때 게시글도 함께 변경된다는 의미입니다.
   * orphanRemoval = true: 유저와 게시글 간의 관계가 끊어지면 해당 게시글도 데이터베이스에서 자동으로 삭제됩니다.
   */
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Todo> todos = new ArrayList<>();

  public User(String email, String username, String password) {
    this.email = email;
    this.username = username;
    this.password = password;

  }
}
