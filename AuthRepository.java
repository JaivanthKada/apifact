package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {
    // Define custom query methods if needed
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
   Optional<User> findByEmail(String Email);
   Optional<User> findByUsername(String Username);
}
