package com.fitness.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegesterRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\\\"\\\\|,.<>/?].*",
            message = "Password must contain at least one special character"
    )
    private String password;
    
    private String firstName;
    private String lastName;
}
