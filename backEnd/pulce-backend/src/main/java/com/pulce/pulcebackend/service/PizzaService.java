package com.pulce.pulcebackend.service;

import com.pulce.pulcebackend.IngredientSearchDTO;
import com.pulce.pulcebackend.PizzaDTO;
import com.pulce.pulcebackend.PizzaSearchDTO;
import com.pulce.pulcebackend.entity.Ingredient;
import com.pulce.pulcebackend.entity.Pizza;
import com.pulce.pulcebackend.repository.IngredientRepository;
import com.pulce.pulcebackend.repository.PizzaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public PizzaSearchDTO createPizza(PizzaDTO dto) {
        Pizza pizza = new Pizza(
                dto.getName(),
                dto.getSellingPrice(),
                dto.getProductionPrice(),
                dto.getType()
        );

        List<Ingredient> ingredients = ingredientRepository.findAllById(dto.getIngredientIds());
        ingredients.forEach(pizza::addIngredient);

        Pizza savedPizza = pizzaRepository.save(pizza);
        return mapToDTO(savedPizza);
    };

    @Transactional
    public PizzaSearchDTO update(int id, PizzaDTO dto) {
        Pizza pizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pizza with id: " + id + " not found"));

        pizza.setName(dto.getName());
        pizza.setSellingPrice(dto.getSellingPrice());
        pizza.setProductionPrice(dto.getProductionPrice());
        pizza.setType(dto.getType());

        pizza.getIngredients().clear();
        List<Ingredient> ingredients = ingredientRepository.findAllById(dto.getIngredientIds());
        ingredients.forEach(pizza::addIngredient);

        Pizza savedPizza = pizzaRepository.save(pizza);
        return mapToDTO(savedPizza);
    }

    public List<PizzaSearchDTO> search(String name, String type) {

        List<Pizza> pizzas;

        if (name != null && type != null) {
            pizzas = pizzaRepository.findByNameContainingIgnoreCaseAndType(name, type);
        } else if (name != null) {
            pizzas = pizzaRepository.findByNameContainingIgnoreCase(name);
        } else if (type != null) {
            pizzas = pizzaRepository.findByType(type);
        } else {
            pizzas = pizzaRepository.findAll();
        }

        return pizzas.stream()
                .map(this::mapToDTO)
                .toList();
    }

    private PizzaSearchDTO mapToDTO(Pizza pizza) {
        List<IngredientSearchDTO> ingredientDTOs = pizza.getIngredients()
                .stream()
                .map(i -> new IngredientSearchDTO(i.getId(), i.getName()))
                .toList();

        return new PizzaSearchDTO(
                pizza.getId(),
                pizza.getName(),
                pizza.getSellingPrice(),
                pizza.getProductionPrice(),
                pizza.getType(),
                ingredientDTOs
        );
    }
}
