package com.namji.todolist.service;

import com.namji.todolist.dto.request.LoginRequest;
import com.namji.todolist.dto.request.SignupRequest;
import com.namji.todolist.dto.response.UserResponse;
import com.namji.todolist.entity.User;
import com.namji.todolist.exception.CustomException;
import com.namji.todolist.exception.ErrorCode;
import com.namji.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserResponse signup(SignupRequest signupRequest) {
    User findUser = userRepository.findByEmail(signupRequest.getEmail());

    if (findUser != null) {
      throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
    }

    String password = passwordEncoder.encode(signupRequest.getPassword());

    User user = new User(
        signupRequest.getEmail(),
        signupRequest.getUsername(),
        password
    );

    userRepository.save(user);

    return new UserResponse(
        signupRequest.getEmail(),
        signupRequest.getUsername(),
        password
    );
  }
}
