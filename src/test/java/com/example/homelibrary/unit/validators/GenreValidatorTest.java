package com.example.homelibrary.unit.validators;

import com.example.homelibrary.utils.validator.GenreValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenreValidatorTest {

    @Mock
    ConstraintValidatorContext constraintValidatorContext;

    private final GenreValidator genreValidator = new GenreValidator();

    @Test
    void whenValidGenreAdded_ReturnsTrue() {
        String validGenre = "LITERATURE";
        assertTrue(genreValidator.isValid(validGenre, constraintValidatorContext));
    }

    @Test
    void whenInvalidGenreAdded_ReturnsFalse() {
        String invalidGenre = "VALAMI";
        assertFalse(genreValidator.isValid(invalidGenre, constraintValidatorContext));
    }
}


