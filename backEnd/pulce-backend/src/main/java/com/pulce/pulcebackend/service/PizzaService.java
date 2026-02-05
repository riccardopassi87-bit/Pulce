package com.pulce.pulcebackend.service;

import com.pulce.pulcebackend.dto.IngredientSearchDTO;
import com.pulce.pulcebackend.dto.PizzaDTO;
import com.pulce.pulcebackend.dto.PizzaSearchDTO;
import com.pulce.pulcebackend.entity.Ingredient;
import com.pulce.pulcebackend.entity.Pizza;
import com.pulce.pulcebackend.mapper.PizzaMapper;
import com.pulce.pulcebackend.repository.IngredientRepository;
import com.pulce.pulcebackend.repository.PizzaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaService {

    private final PizzaRepository pizzaRepository;
    private final IngredientRepository ingredientRepository;
    private final PizzaMapper pizzaMapper;

    public PizzaService(PizzaRepository pizzaRepository, IngredientRepository ingredientRepository, PizzaMapper pizzaMapper) {
        this.pizzaRepository = pizzaRepository;
        this.ingredientRepository = ingredientRepository;
        this.pizzaMapper = pizzaMapper;
    }

    @Transactional
    public PizzaSearchDTO createPizza(PizzaDTO dto) {
        Pizza pizza = pizzaMapper.toEntity(dto);

        List<Ingredient> ingredients = ingredientRepository.findAllById(dto.ingredientIds());
        ingredients.forEach(pizza::addIngredient);

        return pizzaMapper.toSearchDTO(pizzaRepository.save(pizza));
    };

    @Transactional
    public PizzaSearchDTO update(int id, PizzaDTO dto) {
        Pizza pizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pizza with id: " + id + " not found"));

        pizza.setName(dto.name());
        pizza.setSellingPrice(dto.sellingPrice());
        pizza.setProductionPrice(dto.productionPrice());
        pizza.setType(dto.type());

        pizza.getIngredients().clear();
        List<Ingredient> ingredients = ingredientRepository.findAllById(dto.ingredientIds());
        ingredients.forEach(pizza::addIngredient);

        return pizzaMapper.toSearchDTO(pizzaRepository.save(pizza));
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

        return pizzaMapper.toSearchDTOlist(pizzas);
    }

    @Transactional
    public void delete(int id) {
        Pizza pizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pizza with id: " + id + " not found"));

        pizza.getIngredients().clear();
        pizzaRepository.delete(pizza);
    }
}
