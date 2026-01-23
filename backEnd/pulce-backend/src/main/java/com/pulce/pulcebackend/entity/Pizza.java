package com.pulce.pulcebackend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pizzas")

public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal sellingPrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal productionPrice;

    @Column(nullable = false)
    private String type;

    @ManyToMany
    @JoinTable(
            name = "pizza_ingredients",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients = new ArrayList<>();

    protected Pizza() {}

    public Pizza(String name, BigDecimal sellingPrice, BigDecimal productionPrice, String type) {
        this.name = name;
        this.sellingPrice = sellingPrice;
        this.productionPrice = productionPrice;
        this.type = type;
    }

    // GETTERS
    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    // GETTERS
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    //SETTERS

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

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
