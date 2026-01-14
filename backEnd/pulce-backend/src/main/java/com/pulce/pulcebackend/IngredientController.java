package com.pulce.pulcebackend;

import com.pulce.pulcebackend.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ingredient")
@CrossOrigin(origins = "http://localhost:5173")

public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public ResponseEntity<?> createIngredient(@RequestBody IngredientDTO ingredient) {
        ingredientService.createIngredient(ingredient);
        return ResponseEntity.ok().build();
    }
}
