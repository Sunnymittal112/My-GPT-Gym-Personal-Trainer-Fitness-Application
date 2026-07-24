package com.fitness.userservice.repository;

import java.time.LocalDateTime;

public interface UserProfileProjection {
    String getId();

    String getEmail();

    String getFirstName();

    String getLastName();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();
}
