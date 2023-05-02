package com.example.homelibrary.unit.validators;

import com.example.homelibrary.utils.validator.AuthorValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthorValidatorTest {

    @Mock
    ConstraintValidatorContext constraintValidatorContext;

    private final AuthorValidator authorValidator = new AuthorValidator();

    @Test
    void whenValidAuthorAdded_ReturnsTrue(){
        String validAuthor = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzŽžÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõöùúûüýþÿ.-' ";
        assertTrue(authorValidator.isValid(validAuthor, constraintValidatorContext));
    }

    @Test
    void whenInvalidAuthorAdded_ReturnsFalse(){
        String invalidAuthor = "12()";
        assertFalse(authorValidator.isValid(invalidAuthor, constraintValidatorContext));
    }
}



