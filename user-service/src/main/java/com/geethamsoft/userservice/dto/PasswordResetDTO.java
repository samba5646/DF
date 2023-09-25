package com.geethamsoft.userservice.dto;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetDTO {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "OTP is required")
    private String otp;

    @NotBlank(message = "New password is required")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
    private String newPassword;

    @NotBlank(message = "Confirm password is required")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
    private String confirmPassword;
}
