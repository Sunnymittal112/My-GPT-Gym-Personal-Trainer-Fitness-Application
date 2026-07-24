package com.fitness.userservice.service;

import com.fitness.userservice.dto.RegesterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;
import com.fitness.userservice.repository.UserProfileProjection;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse register(RegesterRequest request) {
        User user = new User();
        user.setEmail(normalizeEmail(request.getEmail()));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        try {
            return toResponse(repository.saveAndFlush(user));
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
    }

    @Transactional(readOnly = true)
    public UserResponse getUserProfile(String userId) {
        UserProfileProjection user = repository.findProfileById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return toResponse(user);
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(),
                user.getCreatedAt(), user.getUpdatedAt()
        );
    }

    private UserResponse toResponse(UserProfileProjection user) {
        return new UserResponse(
                user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(),
                user.getCreatedAt(), user.getUpdatedAt()
        );
    }
}
