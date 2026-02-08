package com.pulce.pulcebackend.dto;

public class IngredientSearchDTO {
    private int id;
    private String name;
    private String allergene;

    public IngredientSearchDTO(int id, String name, String allergene) {
        this.id = id;
        this.name = name;
        this.allergene = allergene;
    }

    public int getId() {return id;}
    public String getName() {return name;}
    public String getAllergene() {return allergene;}
}
