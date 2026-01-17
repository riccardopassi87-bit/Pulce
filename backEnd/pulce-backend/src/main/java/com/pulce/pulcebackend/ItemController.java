package com.pulce.pulcebackend;

import com.pulce.pulcebackend.entity.Item;
import com.pulce.pulcebackend.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/item")
@CrossOrigin(origins = "http://localhost:5173")

public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) { this.itemService = itemService; }

    @PostMapping
    public ResponseEntity<Item> createItem (@Valid @RequestBody ItemDTO dto){
        Item created = itemService.create(dto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
