package com.geethamsoft.NearByBloodDonor.validations;

import jakarta.validation.Constraint;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AgeValidatior.class)
@Retention(value = RetentionPolicy.RUNTIME)
@Document
@Target(value = ElementType.FIELD)
public @interface Age {
    public long minimum();
    public long maximum();
    String message() default "Age is not valid";
    Class[] groups() default {};
    Class[] payload() default {};
}
