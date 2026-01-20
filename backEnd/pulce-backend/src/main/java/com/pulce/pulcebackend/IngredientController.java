package com.pulce.pulcebackend;

import com.pulce.pulcebackend.entity.Ingredient;
import com.pulce.pulcebackend.service.IngredientService;
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

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
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

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable int id, @Valid @RequestBody IngredientDTO dto) {
        Ingredient updated = ingredientService.update(id, dto);
        return ResponseEntity.ok(updated);
    }
}
