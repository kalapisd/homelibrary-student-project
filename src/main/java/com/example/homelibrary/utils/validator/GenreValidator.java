package com.example.homelibrary.utils.validator;

import com.example.homelibrary.entity.GenreType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class GenreValidator implements ConstraintValidator<ValidGenre, String> {

    @Override
    public void initialize(ValidGenre constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return Arrays.stream(GenreType.values())
                .anyMatch(genreType -> genreType.name().equals(value));
    }
}
