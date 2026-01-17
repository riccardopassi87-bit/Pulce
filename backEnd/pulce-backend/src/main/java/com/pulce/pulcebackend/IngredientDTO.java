package com.pulce.pulcebackend;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class IngredientDTO {

    @NotBlank
    private String name;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal portionPrice;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal kgPrice;
    @NotBlank
    private String type;
    @NotBlank
    private String allergene;

    // GETTERS

    public String getName() {
        return name;
    }

    public BigDecimal getPortionPrice() {
        return portionPrice;
    }

    public BigDecimal getKgPrice() {
        return kgPrice;
    }

    public String getType() {
        return type;
    }

    public String getAllergene() {
        return allergene;
    }

    // SETTERS

    public void setName(String name) {
        this.name = name;
    }

    public void setPortionPrice(BigDecimal portionPrice) {
        this.portionPrice = portionPrice;
    }

    public void setKgPrice(BigDecimal kgPrice) {
        this.kgPrice = kgPrice;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAllergene(String allergene) {
        this.allergene = allergene;
    }
}