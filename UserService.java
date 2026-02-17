package com.example.demo.service;


import com.example.demo.entity.OldUser;
import com.example.demo.generated.model.AuthResponse;
import com.example.demo.generated.model.UserLoginRequest;
import com.example.demo.generated.model.UserRegisterRequest;
import com.example.demo.generated.model.UserResponse;
import com.example.demo.repository.OldUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for user operations
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final OldUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Register a new user
     */
    public UserResponse registerUser(UserRegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already taken");
        }

        // Create new user
        OldUser user = OldUser.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .build();

        // Save user to database
        OldUser savedUser = userRepository.save(user);

        // Return user response
        return mapToResponse(savedUser);
    }

    /**
     * Login user
     */
    public AuthResponse loginUser(UserLoginRequest request) {
        // Find user by email
        OldUser user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Generate refresh token (stubbed for now)
        String refreshToken = generateRefreshToken(user);

        // Return AuthResponse with user and refresh token
        AuthResponse authResponse = new AuthResponse();
        authResponse.setUser(mapToResponse(user));
        authResponse.setRefreshToken(refreshToken);
        return authResponse;
    }

    private String generateRefreshToken(OldUser user) {
        // TODO: Implement real refresh token logic (JWT or random string)
        return "dummy-refresh-token-" + user.getId();
    }

    /**
     * Map OldUser entity to UserResponse DTO
     */
    private UserResponse mapToResponse(OldUser user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        return response;
    }
}
