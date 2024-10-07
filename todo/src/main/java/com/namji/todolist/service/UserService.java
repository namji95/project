package com.namji.todolist.service;

import com.namji.todolist.dto.request.UserRequest;
import com.namji.todolist.dto.response.UserResponse;
import com.namji.todolist.entity.User;
import com.namji.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public UserResponse createUser(UserRequest userRequest) {
    User findUser = userRepository.findByEmail(userRequest.getEmail());

    if (findUser != null) {
      throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
    }

    User user = new User(
        userRequest.getEmail(),
        userRequest.getUsername(),
        userRequest.getPassword()
    );

    userRepository.save(user);

    return new UserResponse(
        userRequest.getEmail(),
        userRequest.getUsername(),
        userRequest.getPassword()
    );
  }
}
