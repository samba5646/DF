package com.geethamsoft.NearByBloodDonor.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Validator;

public class AgeValidatior implements ConstraintValidator<Age, Number> {

    private long minimum;
    private long maximum;

    @Override
    public void initialize(Age constraintAnnotation) {
        minimum = constraintAnnotation.minimum();
        maximum = constraintAnnotation.maximum();
    }

    @Override
    public boolean isValid(Number number, ConstraintValidatorContext constraintValidatorContext) {
        long val = number.longValue();
        return (val >= minimum && val <= maximum);
    }
}
