package com.pulce.pulcebackend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingredients")

public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal portionPrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal kgPrice;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String allergene;

    @ManyToMany(mappedBy = "ingredients")
    private List<Pizza> pizzas =  new ArrayList<>();

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    protected Ingredient() {}

    public Ingredient(String name, String type, BigDecimal portionPrice, BigDecimal kgPrice, String allergene) {
        this.name = name;
        this.type = type;
        this.portionPrice = portionPrice;
        this.kgPrice = kgPrice;
        this.allergene = allergene;
    }
    public int getId() {
        return id;
    }

    //GETTERS
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

    //SETTERS

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
