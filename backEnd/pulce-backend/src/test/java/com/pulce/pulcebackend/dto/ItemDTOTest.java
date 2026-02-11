package com.pulce.pulcebackend.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemDTOTest {
    private static Validator validator;

    @SuppressWarnings("resource")
    @BeforeAll
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test // NO ERROR
    void testItemDTO() {
        ItemDTO item = new ItemDTO("Krumiri", new BigDecimal("1.5"), new BigDecimal("5"), "Bisquits", LocalDate.now().plusDays(7), 10);

        assertThat(validator.validate(item)).isEmpty();
    }

    @Test // ERROR
    void blankName_Fails() {
        Set<ConstraintViolation<ItemDTO>> violations = validator.validate(new ItemDTO("", new BigDecimal("1.5"), new BigDecimal("5"), "Bisquits", LocalDate.now().plusDays(7), 10));

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(
                v -> v.getPropertyPath().toString().contains("name")));
    }

    @Test // ERROR
    void zeroOriginalPrice_Fails() {
        Set<ConstraintViolation<ItemDTO>> violations = validator.validate(new ItemDTO("Krumiri", BigDecimal.ZERO, new BigDecimal("5"), "Bisquits", LocalDate.now().plusDays(7), 10));

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(
                v -> v.getPropertyPath().toString().contains("originalPrice")));
    }

    @Test //ERROR
    void nullSellingPrice_Fails() {
        Set<ConstraintViolation<ItemDTO>> violations = validator.validate(new ItemDTO("Krumiri", new BigDecimal("1.5"), null, "Bisquits", LocalDate.now().plusDays(7), 10));

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(
                v -> v.getPropertyPath().toString().contains("sellingPrice")));
    }

    @Test //ERROR
    void blankType_Fails() {
        Set<ConstraintViolation<ItemDTO>> violations = validator.validate(new ItemDTO("Krumiri", new BigDecimal("1.5"), new BigDecimal("5"), "", LocalDate.now().plusDays(7), 10));

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(
                v -> v.getPropertyPath().toString().contains("type")));
    }

    @Test // ERROR
    void nullExpirationDate_Fails() {
        Set<ConstraintViolation<ItemDTO>> violations = validator.validate(new ItemDTO("Krumiri", new BigDecimal("1.5"), new BigDecimal("5"), "Bisquits", null, 10));

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(
                v -> v.getPropertyPath().toString().contains("expirationDate")));
    }

    @Test // ERROR
    void zeroAmount_Fails() {
        Set<ConstraintViolation<ItemDTO>> violations = validator.validate(new ItemDTO("Krumiri", new BigDecimal("1.5"), new BigDecimal("5"), "", LocalDate.now().plusDays(7), 0));

        assertEquals(2, violations.size()); // Is 0 and less than 1
        assertTrue(violations.stream().anyMatch(
                v -> v.getPropertyPath().toString().contains("amount")));
    }

    @Test
    void accessorsReturnCorrectValues() {
        ItemDTO item = new ItemDTO("Krumiri", new BigDecimal("1.5"), new BigDecimal("5"), "Bisquits", LocalDate.now().plusDays(7), 10);

        assertEquals("Krumiri", item.name());
        assertEquals(new BigDecimal("1.5"), item.originalPrice());
        assertEquals(new BigDecimal("5"), item.sellingPrice());
        assertEquals("Bisquits", item.type());
        assertEquals(LocalDate.now().plusDays(7), item.expirationDate());
        assertEquals(10, item.amount());
    }
}
