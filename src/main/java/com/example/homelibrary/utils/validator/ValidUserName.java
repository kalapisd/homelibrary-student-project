package com.example.homelibrary.utils.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUserName {
    String message() default "Username must be valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
