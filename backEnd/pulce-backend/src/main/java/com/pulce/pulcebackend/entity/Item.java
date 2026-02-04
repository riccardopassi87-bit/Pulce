package com.pulce.pulcebackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "items")

public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal originalPrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal sellingPrice;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @Min(1)
    @Column(nullable = false)
    private int amount;

    protected Item() {}

    public Item(String name, BigDecimal originalPrice, BigDecimal sellingPrice, String type, LocalDate expirationDate, int amount) {
        this.name = name;
        this.originalPrice = originalPrice;
        this.sellingPrice = sellingPrice;
        this.type = type;
        this.expirationDate = expirationDate;
        this.amount = amount;
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public String getType() {
        return type;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public int getAmount() {
        return amount;
    }

    // SETTERS


    public void setName(String name) {
        this.name = name;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
