package com.pulce.pulcebackend.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public class PizzaDTO {

    @NotBlank
    private String name;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal sellingPrice;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal productionPrice;

    @NotBlank
    private String type;

    @NotEmpty
    private List<Integer> ingredientIds;

    // GETTERS
    public String getName() {
        return name;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public BigDecimal getProductionPrice() {
        return productionPrice;
    }

    public String getType() {
        return type;
    }

    public List<Integer> getIngredientIds() {
        return ingredientIds;
    }

    // SETTERS

    public void setName(String name) {
        this.name = name;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setProductionPrice(BigDecimal productionPrice) {
        this.productionPrice = productionPrice;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIngredientIds(List<Integer> ingredientIds) {
        this.ingredientIds = ingredientIds;
    }
}
