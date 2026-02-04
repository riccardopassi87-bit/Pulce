package com.pulce.pulcebackend.dto;

public class IngredientSearchDTO {
    private int id;
    private String name;

    public IngredientSearchDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {return id;}
    public String getName() {return name;}
}
