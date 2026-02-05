package com.pulce.pulcebackend.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record PizzaDTO
   (@NotBlank String name,
    @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal sellingPrice,
    @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal productionPrice,
    @NotBlank String type,
    @NotEmpty List<Integer> ingredientIds) {}
