package com.example.demo.controller;

import com.example.demo.generated.api.UserApi;
import com.example.demo.generated.model.AuthResponse;
import com.example.demo.generated.model.UserLoginRequest;
import com.example.demo.generated.model.UserRegisterRequest;
import com.example.demo.generated.model.UserResponse;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API Controller for user operations
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "APIs for user registration and authentication")
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<UserResponse> oldRegisterUser(@Valid UserRegisterRequest userRegisterRequest) {
        UserResponse response = userService.registerUser(userRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<AuthResponse> oldLoginUser(@Valid UserLoginRequest userLoginRequest) {
        com.example.demo.generated.model.AuthResponse response = userService.loginUser(userLoginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
