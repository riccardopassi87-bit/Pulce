package com.pulce.pulcebackend;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Date;

public class ItemDTO {

    @NotBlank
    private String name;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal originalPrice;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal sellingPrice;
    @NotBlank
    private String type;
    @NotBlank
    private Date expirationDate;
    @NotNull
    @DecimalMin(value = "0", inclusive = false)
    private int amount;

    // GETTERS
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

    //SETTERS

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

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}





