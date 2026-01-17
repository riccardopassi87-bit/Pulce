package com.pulce.pulcebackend.service;

import com.pulce.pulcebackend.entity.Ingredient;
import com.pulce.pulcebackend.IngredientDTO;
import com.pulce.pulcebackend.repository.IngredientRepository;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient create (IngredientDTO dto) {
        Ingredient ingredient = new Ingredient(
                dto.getName(),
                dto.getType(),
                dto.getPortionPrice(),
                dto.getKgPrice(),
                dto.getAllergene()
        );

        return ingredientRepository.save(ingredient);
    }
}
