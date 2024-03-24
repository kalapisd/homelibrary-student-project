package com.example.homelibrary.utils.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Password must include both lower and upper case characters, at least one number and a symbol";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
