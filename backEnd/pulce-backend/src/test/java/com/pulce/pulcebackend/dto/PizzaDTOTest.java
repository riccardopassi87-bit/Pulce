package com.pulce.pulcebackend.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PizzaDTOTest {
    private static Validator validator;

    @SuppressWarnings("resource")
    @BeforeAll
    static void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void validPizza_NoViolations(){
        PizzaDTO dto = new PizzaDTO("Margherita", new BigDecimal("11.00"), new BigDecimal("5"), "normal", List.of(1,2,3));

        Set<ConstraintViolation<PizzaDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void blankName_Fails() {
        Set<ConstraintViolation<PizzaDTO>> violations = validator.validate(new PizzaDTO("", new BigDecimal("11.00"), new BigDecimal("5"), "normal", List.of(1,2,3)));

        assertEquals(1, violations.size());
    }

    @Test
    void zeroSellingPrice_Fails() {
        Set<ConstraintViolation<PizzaDTO>> violations = validator.validate(new PizzaDTO("Margherita", BigDecimal.ZERO, new BigDecimal("5"), "normal", List.of(1,2,3)));

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(
                violation -> violation.getPropertyPath().toString().equals("sellingPrice")));
    }

    @Test
    void nullProductionPrice_Fails() {
        Set<ConstraintViolation<PizzaDTO>> violations = validator.validate(new PizzaDTO("Margherita", new BigDecimal("11.00"), null, "normal", List.of(1,2,3)));

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(
                violation -> violation.getPropertyPath().toString().equals("productionPrice")));
    }

    @Test
    void blankType_Fails() {
        Set<ConstraintViolation<PizzaDTO>> violations = validator.validate(new PizzaDTO("Margherita", new BigDecimal("11.00"), new BigDecimal("5"), "", List.of(1,2,3)));

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(
                violation -> violation.getPropertyPath().toString().equals("type")));
    }

    @Test
    void emptyIngredientIds_Fails() {
        Set<ConstraintViolation<PizzaDTO>> violations = validator.validate(new PizzaDTO("Margherita", new BigDecimal("11.00"), new BigDecimal("5"), "normal", List.of()));

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(
                violation -> violation.getPropertyPath().toString().equals("ingredientIds")));
    }

    @Test
    void accessorsReturnCorrectValues() {
        PizzaDTO dto = new PizzaDTO("Margherita", new BigDecimal("11.00"), new BigDecimal("5"), "normal", List.of(1,2,3));

        assertEquals("Margherita", dto.name());
        assertEquals(new BigDecimal("11.00"), dto.sellingPrice());
        assertEquals(new BigDecimal("5"), dto.productionPrice());
        assertEquals("normal", dto.type());
        assertEquals(List.of(1,2,3), dto.ingredientIds());
    }
}
