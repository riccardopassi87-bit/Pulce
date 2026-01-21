package com.pulce.pulcebackend;

import com.pulce.pulcebackend.entity.Pizza;
import com.pulce.pulcebackend.service.PizzaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizza")
@CrossOrigin(origins = "http://localhost:5173")
public class PizzaController {

    private final PizzaService pizzaService;

    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @PostMapping
    public ResponseEntity<Pizza> createPizza(@Valid @RequestBody PizzaDTO dto) {
        Pizza createdPizza = pizzaService.createPizza(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPizza);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PizzaSearchDTO>> searchPizza (
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type
    ){
        return ResponseEntity.ok(pizzaService.search(name, type));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pizza> updatePizza(@PathVariable int id, @Valid @RequestBody PizzaDTO dto) {
        Pizza updated = pizzaService.update(id, dto);
        return ResponseEntity.ok(updated);
    }
}
