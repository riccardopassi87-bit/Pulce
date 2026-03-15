package com.pulce.pulcebackend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import java.time.temporal.ChronoUnit;
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate expirationDate;

    @Min(1)
    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private int lastNotificationSent = 99;

    protected Item() {}

    public Item(String name, BigDecimal originalPrice, BigDecimal sellingPrice, String type, LocalDate expirationDate, int amount) {
        this.name = name;
        this.originalPrice = originalPrice;
        this.sellingPrice = sellingPrice;
        this.type = type;
        this.expirationDate = expirationDate;
        this.amount = amount;
    }

    @Transient
    public String getUrgency(){
        if (expirationDate == null) return "UNKNOWN";

        long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), expirationDate);

        // Still ADD EXPIRED LOGIC AND EMAIL

        if (daysLeft <= 7) return "URGENT";
        if (daysLeft <= 20) return "WARNING";
        if (daysLeft <= 30) return "UPCOMING";
        return "OK";
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

    public int getLastNotificationSent() {
        return lastNotificationSent;
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

    public void setLastNotificationSent(int lastNotificationSent) {
        this.lastNotificationSent = lastNotificationSent;
    }
}
