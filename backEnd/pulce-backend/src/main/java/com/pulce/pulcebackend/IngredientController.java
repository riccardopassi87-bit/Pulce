package com.pulce.pulcebackend;

import com.pulce.pulcebackend.entity.Ingredient;
import com.pulce.pulcebackend.entity.Pizza;
import com.pulce.pulcebackend.repository.PizzaRepository;
import com.pulce.pulcebackend.service.IngredientService;
import com.pulce.pulcebackend.service.PizzaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/ingredient")
@CrossOrigin(origins = "http://localhost:5173")

public class IngredientController {

    private final IngredientService ingredientService;
    private final PizzaRepository pizzaRepository;

    public IngredientController(IngredientService ingredientService, PizzaRepository pizzaRepository) {
        this.ingredientService = ingredientService;
        this.pizzaRepository = pizzaRepository;
    }

    @PostMapping
    public ResponseEntity<Ingredient> createIngredient(@Valid @RequestBody IngredientDTO dto) {
        Ingredient created = ingredientService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Ingredient>> searchIngredients(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type
    ) {
        return ResponseEntity.ok(ingredientService.search(name,type));
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable int id, @Valid @RequestBody IngredientDTO dto) {
        Ingredient updated = ingredientService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable int id) {
        ingredientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/impact")
    public ResponseEntity<List<String>> getUpdateImpact(@PathVariable int id) {
        List<Pizza> linkedPizzas = pizzaRepository.findByIngredientsId(id);
        List<String> pizzaNames = linkedPizzas.stream()
                .map(Pizza::getName)
                .toList();
        return ResponseEntity.ok(pizzaNames);
    }
}
