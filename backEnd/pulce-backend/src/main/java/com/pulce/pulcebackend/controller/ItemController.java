package com.pulce.pulcebackend.controller;

import com.pulce.pulcebackend.dto.ExpirationAlertsDTO;
import com.pulce.pulcebackend.dto.ItemDTO;
import com.pulce.pulcebackend.dto.ItemReductionDTO;
import com.pulce.pulcebackend.entity.Item;
import com.pulce.pulcebackend.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "chrome-extension://hcmaackdifnboaddamjeapdfinljncda"})

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
    public ResponseEntity<ExpirationAlertsDTO> getExpiringItem(){

        ExpirationAlertsDTO alerts = new ExpirationAlertsDTO(
                itemService.getUrgentExpiration(),
                itemService.getWarningExpiration(),
                itemService.getUpcomingExpiration()
        );

        return ResponseEntity.ok(alerts);
    }

    @PostMapping
    public ResponseEntity<Item> createItem (@Valid @RequestBody ItemDTO dto){
        Item created = itemService.create(dto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/reduce")
    public ResponseEntity<?> reduceItem (@RequestBody List<ItemReductionDTO> items){
        itemService.updateStock(items);
        return ResponseEntity.ok().build();
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
