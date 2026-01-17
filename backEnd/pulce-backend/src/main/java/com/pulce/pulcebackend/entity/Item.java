package com.pulce.pulcebackend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

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
    private Date expirationDate;

    @Column(nullable = false)
    private int amount;

    protected Item() {}

    public Item(String name, BigDecimal originalPrice, BigDecimal sellingPrice, String type, Date expirationDate, int amount) {
        this.name = name;
        this.originalPrice = originalPrice;
        this.sellingPrice = sellingPrice;
        this.type = type;
        this.expirationDate = expirationDate;
        this.amount = amount;
    }

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

    public Date getExpirationDate() {
        return expirationDate;
    }

    public int getAmount() {
        return amount;
    }
}
