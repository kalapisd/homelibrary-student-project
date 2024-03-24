package com.example.homelibrary.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<ValidUserName, String> {

    @Override
    public void initialize(ValidUserName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value.matches("^[a-zA-Z][a-zA-Z0-9_]{4,10}");
    }
}
