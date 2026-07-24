package com.fitness.userservice.repository;

import com.fitness.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    @Query("""
            select u.id as id, u.email as email, u.firstName as firstName, u.lastName as lastName,
                   u.createdAt as createdAt, u.updatedAt as updatedAt
            from User u
            where u.id = :id
            """)
    Optional<UserProfileProjection> findProfileById(String id);
}
