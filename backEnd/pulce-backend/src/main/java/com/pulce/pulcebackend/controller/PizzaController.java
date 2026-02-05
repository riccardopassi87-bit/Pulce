package com.pulce.pulcebackend.controller;

import com.pulce.pulcebackend.dto.PizzaDTO;
import com.pulce.pulcebackend.dto.PizzaSearchDTO;
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
    public ResponseEntity<PizzaSearchDTO> createPizza(@Valid @RequestBody PizzaDTO dto) {
        PizzaSearchDTO createdPizza = pizzaService.createPizza(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPizza);
    }

    @GetMapping
    public ResponseEntity<List<PizzaSearchDTO>> getPizza (
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type
    ){
        return ResponseEntity.ok(pizzaService.search(name, type));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PizzaSearchDTO> updatePizza(@PathVariable int id, @Valid @RequestBody PizzaDTO dto) {

        return ResponseEntity.ok(pizzaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePizza(@PathVariable int id) {
        pizzaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
