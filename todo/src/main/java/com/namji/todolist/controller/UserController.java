package com.namji.todolist.controller;

import com.namji.todolist.dto.request.UserRequest;
import com.namji.todolist.dto.response.UserResponse;
import com.namji.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public UserResponse createUser (@RequestBody UserRequest userRequest) {
    return userService.createUser(userRequest);
  }
}
