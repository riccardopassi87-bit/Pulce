package com.pulce.pulcebackend.controller;

import com.pulce.pulcebackend.dto.IngredientDTO;
import com.pulce.pulcebackend.entity.Ingredient;
import com.pulce.pulcebackend.entity.Pizza;
import com.pulce.pulcebackend.repository.PizzaRepository;
import com.pulce.pulcebackend.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ingredient")
@CrossOrigin(origins = "http://localhost:5173")

public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> searchIngredients(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type
    ) {
        return ResponseEntity.ok(ingredientService.search(name,type));
    }

    @GetMapping("/{id}/impact")
    public ResponseEntity<List<String>> getUpdateImpact(@PathVariable int id) {
        return ResponseEntity.ok(ingredientService.getImpactNames(id));
    }

    @PostMapping
    public ResponseEntity<Ingredient> createIngredient(@Valid @RequestBody IngredientDTO dto) {
        Ingredient created = ingredientService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable int id, @Valid @RequestBody IngredientDTO dto) {
        return ResponseEntity.ok(ingredientService.update(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIngredient(@PathVariable int id) {
        try {
            ingredientService.delete(id);
            return ResponseEntity.noContent().build(); // Return 204 on success
        } catch (IllegalStateException e) {
            // We catch the error here and send it back as a 409 Conflict
            // with the specific pizza list message
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}/cascade")
    public ResponseEntity<?> deleteCascade(@PathVariable int id) {
        ingredientService.deleteWithPizzas(id);
        return ResponseEntity.ok().build();
    }
}
