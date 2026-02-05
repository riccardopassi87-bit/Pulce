package com.pulce.pulcebackend.controller;

import com.pulce.pulcebackend.dto.ItemDTO;
import com.pulce.pulcebackend.entity.Item;
import com.pulce.pulcebackend.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/item")
@CrossOrigin(origins = "http://localhost:5173")

public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) { this.itemService = itemService; }

    @GetMapping
    public ResponseEntity<List<Item>> searchItem (
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type
    ){
        return ResponseEntity.ok(itemService.search(name,type));
    }

    @GetMapping("/expiring")
    public ResponseEntity<Map<String, List<Item>>> getExpiringItem(){

        Map<String,List<Item>> expiring = new HashMap<>();

        expiring.put("urgent", itemService.getUrgentExpiration());
        expiring.put("warning", itemService.getWarningExpiration());
        expiring.put("upcoming", itemService.getUpcomingExpiration());

        return ResponseEntity.ok(expiring);
    }

    @PostMapping
    public ResponseEntity<Item> createItem (@Valid @RequestBody ItemDTO dto){
        Item created = itemService.create(dto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable int id, @Valid @RequestBody ItemDTO dto){
        return ResponseEntity.ok(itemService.update(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Item> deleteItem(@PathVariable int id){
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
