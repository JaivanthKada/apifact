package com.example.demo.repository;

import com.example.demo.entity.OldUser;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for User entity
 */
@Repository
public interface OldUserRepository extends JpaRepository<OldUser, Long> {

    /**
     * Find a user by email
     */
    Optional<OldUser> findByEmail(String email);

    /**
     * Check if user exists by email
     */
    boolean existsByEmail(String email);

    /**
     * Check if user exists by username
     */
    boolean existsByUsername(String username);
}

