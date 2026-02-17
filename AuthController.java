package com.example.demo.controller;


import com.example.demo.generated.api.AuthApi;
import com.example.demo.generated.model.LoginRequestDTO;
import com.example.demo.generated.model.LoginResponseDTO;
import com.example.demo.generated.model.RegisterRequestDTO;
import com.example.demo.generated.model.RegisterResponseDTO;
import com.example.demo.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Authentication APIs")
public class AuthController implements AuthApi {

    public final AuthService authService;

    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@Valid LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO response = authService.loginUser(loginRequestDTO);
        return new ResponseEntity<>( response, HttpStatus.CREATED);
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> registerUser(@Valid RegisterRequestDTO registerRequestDTO) {
        RegisterResponseDTO response = authService.registerUser(registerRequestDTO);
        return new ResponseEntity<>( response, HttpStatus.CREATED);
    }
}
