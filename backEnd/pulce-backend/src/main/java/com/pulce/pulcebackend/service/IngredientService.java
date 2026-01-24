package com.pulce.pulcebackend.service;

import com.pulce.pulcebackend.entity.Ingredient;
import com.pulce.pulcebackend.IngredientDTO;
import com.pulce.pulcebackend.entity.Pizza;
import com.pulce.pulcebackend.repository.IngredientRepository;
import com.pulce.pulcebackend.repository.PizzaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    public List<Ingredient> search(String name, String type){

        if(name != null && type != null){
            return ingredientRepository.findByNameContainingIgnoreCaseAndType(name, type);
        }
        if(name != null){
            return ingredientRepository.findByNameContainingIgnoreCase(name);
        }
        if(type != null){
            return ingredientRepository.findByType(type);
        }
        return ingredientRepository.findAll();
    }

    private final IngredientRepository ingredientRepository;
    private final PizzaRepository pizzaRepository;

    public IngredientService(IngredientRepository ingredientRepository, PizzaRepository pizzaRepository) {
        this.ingredientRepository = ingredientRepository;
        this.pizzaRepository = pizzaRepository;
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

    public Ingredient update(int id, IngredientDTO dto) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));

        ingredient.setName(dto.getName());
        ingredient.setType(dto.getType());
        ingredient.setPortionPrice(dto.getPortionPrice());
        ingredient.setKgPrice(dto.getKgPrice());
        ingredient.setAllergene(dto.getAllergene());
        
        return ingredientRepository.save(ingredient);
    }

    public void delete(int id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));

        List<Pizza> linkedPizzas = pizzaRepository.findByIngredientsId(id);

        if(!linkedPizzas.isEmpty()){
            List<String> pizzaNames = linkedPizzas.stream()
                    .map(Pizza::getName)
                    .toList();

            throw new IllegalStateException("Cannot delete '" + ingredient.getName() +
                    "'. It is used in the following pizzas: " + String.join(", ", pizzaNames));
        }
        ingredientRepository.delete(ingredient);
    }

    public List<Ingredient> findAll(){
        return (List<Ingredient>) ingredientRepository.findAll();
    }
}
