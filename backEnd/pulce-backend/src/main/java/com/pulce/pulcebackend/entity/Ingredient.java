package com.pulce.pulcebackend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

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
}
