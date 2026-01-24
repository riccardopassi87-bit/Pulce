package com.pulce.pulcebackend;

import com.pulce.pulcebackend.ItemDTO;
import com.pulce.pulcebackend.entity.Item;
import com.pulce.pulcebackend.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

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

    @GetMapping("/search")
    public ResponseEntity<List<Item>> searchItem (
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type
    ){
        return ResponseEntity.ok(itemService.search(name,type));
    }

    @GetMapping
    public List<Item> getAllItem(){
        return itemService.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable int id, @Valid @RequestBody ItemDTO dto){
        Item updated = itemService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Item> deleteItem(@PathVariable int id){
        itemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
