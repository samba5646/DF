package com.geethamsoft.userservice.validation;


import com.geethamsoft.userservice.dto.UserRegistrationDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        UserRegistrationDTO user = (UserRegistrationDTO) obj;
        return user.getPassword().equals(user.getConfirmPassword());
    }
}

