package com.pulce.pulcebackend.service;

import com.pulce.pulcebackend.PizzaDTO;
import com.pulce.pulcebackend.entity.Ingredient;
import com.pulce.pulcebackend.entity.Pizza;
import com.pulce.pulcebackend.repository.IngredientRepository;
import com.pulce.pulcebackend.repository.PizzaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaService {

    private final PizzaRepository pizzaRepository;
    private final IngredientRepository ingredientRepository;

    public PizzaService(PizzaRepository pizzaRepository, IngredientRepository ingredientRepository) {
        this.pizzaRepository = pizzaRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Transactional
    public Pizza createPizza(PizzaDTO dto) {

        Pizza pizza = new Pizza(
          dto.getName(),
          dto.getSellingPrice(),
          dto.getProductionPrice(),
          dto.getType()
        );

        List<Ingredient> ingredients = ingredientRepository.findAllById(dto.getIngredientIds());

        ingredients.forEach(pizza::addIngredient);

        return pizzaRepository.save(pizza);
    }
}
