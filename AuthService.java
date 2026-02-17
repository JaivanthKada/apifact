package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.generated.model.LoginRequestDTO;
import com.example.demo.generated.model.LoginResponseDTO;
import com.example.demo.generated.model.RegisterRequestDTO;
import com.example.demo.generated.model.RegisterResponseDTO;
import com.example.demo.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    public final AuthRepository authRepository;
    public final PasswordEncoder passwordEncoder;

    public RegisterResponseDTO registerUser(RegisterRequestDTO registerRequestDTO) {
        if( authRepository.existsByEmail(registerRequestDTO.getEmail())){
            throw new RuntimeException("email  already exists");
        }
        if( authRepository.existsByUsername(registerRequestDTO.getUsername())){
            throw new RuntimeException("username already exists");
        }
        User user = User.builder()
                .username(registerRequestDTO.getUsername())
                .email(registerRequestDTO.getEmail())
                .role( registerRequestDTO.getRole() != null ? registerRequestDTO.getRole().name() : "USER") // Default to USER role if not provided
                .password(passwordEncoder.encode(registerRequestDTO.getPassword())) // In production, hash the password before saving
                .build();
        user = authRepository.save(user);
        return  new RegisterResponseDTO()
                .userId( user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .message("User registered successfully");
    }

    public LoginResponseDTO loginUser( LoginRequestDTO loginRequestDTO) {
       User user = authRepository.findByUsername(loginRequestDTO.getUsernameOrEmail())
                        .or(() -> authRepository.findByEmail(loginRequestDTO.getUsernameOrEmail()))
                        .orElseThrow(() -> new RuntimeException("User not found with username or email: " + loginRequestDTO.getUsernameOrEmail()));
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return new LoginResponseDTO()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role( user.getRole());
    }
}
