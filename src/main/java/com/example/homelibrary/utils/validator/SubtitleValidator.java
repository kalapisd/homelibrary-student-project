package com.example.homelibrary.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SubtitleValidator implements ConstraintValidator<ValidSubTitle, String> {

    @Override
    public void initialize(ValidSubTitle constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value == null || value.isEmpty() || value.matches("^(?:[\\p{L}\\p{Mn}\\p{Pd}0-9;:!?.,()'-]+\\s?)+$");
    }
}
