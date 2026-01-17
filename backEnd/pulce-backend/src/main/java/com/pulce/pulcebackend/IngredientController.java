package com.pulce.pulcebackend;

import com.pulce.pulcebackend.entity.Ingredient;
import com.pulce.pulcebackend.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

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
}
