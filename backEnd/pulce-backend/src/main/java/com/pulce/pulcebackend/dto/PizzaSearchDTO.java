package com.pulce.pulcebackend.dto;

import java.math.BigDecimal;
import java.util.List;

public class PizzaSearchDTO {

    private int id;
    private String name;
    private BigDecimal sellingPrice;
    private BigDecimal productionPrice;
    private String type;
    private List<IngredientSearchDTO> ingredients;

    public PizzaSearchDTO(
            int id,
            String name,
            BigDecimal sellingPrice,
            BigDecimal productionPrice,
            String type,
            List<IngredientSearchDTO> ingredients
    ) {
        this.id = id;
        this.name = name;
        this.sellingPrice = sellingPrice;
        this.productionPrice = productionPrice;
        this.type = type;
        this.ingredients = ingredients;
    }

    //GETTERS

    public int getId() {
        return id;
    }

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

    public List<IngredientSearchDTO> getIngredients() {
        return ingredients;
    }
}
