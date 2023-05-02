package com.example.homelibrary.unit.validators;

import com.example.homelibrary.utils.validator.TitleValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TitleValidatorTest {

    @Mock
    ConstraintValidatorContext constraintValidatorContext;

    private final TitleValidator titleValidator = new TitleValidator();

    @Test
    void whenValidTitleAdded_ReturnsTrue(){
        String validTitle = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzŽžÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõöùúûüýþÿ0123456789;:!?.,()' ";
        assertTrue(titleValidator.isValid(validTitle, constraintValidatorContext));
    }

    @Test
    void whenInvalidTitleAdded_ReturnsFalse(){
        String invalidTitle = "$";
        assertFalse(titleValidator.isValid(invalidTitle, constraintValidatorContext));
    }
}
