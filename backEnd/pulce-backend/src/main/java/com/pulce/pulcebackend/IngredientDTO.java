package com.pulce.pulcebackend;

import java.math.BigDecimal;

public class IngredientDTO {
    private String name;
    private BigDecimal portionPrice;
    private BigDecimal kgPrice;
    private String type;
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

    @Override
    public String toString() {
        return "IngredientDTO{" +
                "name='" + name + '\'' +
                ", portionPrice=" + portionPrice +
                ", kgPrice=" + kgPrice +
                ", type='" + type + '\'' +
                ", allergene='" + allergene + '\'' +
                '}';
    }

}