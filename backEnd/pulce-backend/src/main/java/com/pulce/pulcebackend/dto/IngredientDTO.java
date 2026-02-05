package com.pulce.pulcebackend.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record IngredientDTO
    (@NotBlank String name,
    @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal portionPrice,
    @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal kgPrice,
    @NotBlank String type,
    @NotBlank String allergene)
{}