package com.example.homelibrary.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value.matches("(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\W_])[A-Za-z\\d\\W_]{8,20}");
    }
}

