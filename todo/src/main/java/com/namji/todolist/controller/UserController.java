package com.namji.todolist.controller;

import com.namji.todolist.dto.request.LoginRequest;
import com.namji.todolist.dto.request.SignupRequest;
import com.namji.todolist.dto.response.UserResponse;
import com.namji.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/login-page")
  public String loginPage() {
    return "login";
  }

  @PostMapping("/signup")
  public UserResponse signup (@RequestBody SignupRequest signupRequest) {
    return userService.signup(signupRequest);
  }
}
