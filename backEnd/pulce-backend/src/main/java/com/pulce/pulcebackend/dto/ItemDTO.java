package com.pulce.pulcebackend.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ItemDTO
    (@NotBlank String name,
    @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal originalPrice,
    @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal sellingPrice,
    @NotBlank String type,
    @NotNull LocalDate expirationDate,
    @Min(1) int amount){}






