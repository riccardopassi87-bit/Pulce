package com.pulce.pulcebackend.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class IngredientDTOTest {

    private static Validator validator;

    @SuppressWarnings("resource")
    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test // NO ERRORS
    void name() {
        IngredientDTO dto = new IngredientDTO("Tomato", new BigDecimal("1.5"), new BigDecimal("4.99"), "Veggie", "Sulfite");

        assertEquals("Tomato", dto.name());
        assertThat(validator.validate(dto).isEmpty());
    }

    @Test // ERROR
    void zeroPortionPrice_Fails() {
        IngredientDTO dto = new IngredientDTO("Tomato", BigDecimal.ZERO, new BigDecimal("4.99"), "Veggie", "Sulfite");

        Set<ConstraintViolation<IngredientDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().contains("portionPrice")));
    }

    @Test // ERROR
    void kgPrice_NullValidationFails() {
        IngredientDTO dto = new IngredientDTO("Tomato", new BigDecimal("1.5"), null, "Veggie", "Sulfite");

        Set<ConstraintViolation<IngredientDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().contains("kgPrice")));
    }

    @Test // NO ERROR
    void type() {
        IngredientDTO dto = new IngredientDTO("Tomato", new BigDecimal("1.5"), new BigDecimal("4.99"), "Veggie", "Sulfite");

        assertEquals("Veggie", dto.type());
    }

    @Test // ERROR
    void allergene_BlankFeils() {
        IngredientDTO dto = new IngredientDTO("Tomato", new BigDecimal("1.5"), new BigDecimal("4.99"), "Veggie", "");

        Set<ConstraintViolation<IngredientDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().contains("allergene")));
    }

    @Test
    void equalsHashCodeContract() {
        IngredientDTO dto1 = new IngredientDTO("Tomato", new BigDecimal("1.5"), new BigDecimal("4.99"), "Veggie", "Sulfite");
        IngredientDTO dto2 = new IngredientDTO("Tomato", new BigDecimal("1.5"), new BigDecimal("4.99"), "Veggie", "Sulfite");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}