package com.geethamsoft.userservice.dto;
import com.geethamsoft.userservice.validation.PasswordMatches;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class PasswordChangeDTO {
    @NotBlank(message = "New password is required")
    @Size(min = 6, message = "New password should be at least 6 characters")
    private String newPassword;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;

}
