package com.pulce.pulcebackend.service;

import com.pulce.pulcebackend.ItemDTO;
import com.pulce.pulcebackend.entity.Ingredient;
import com.pulce.pulcebackend.entity.Item;
import com.pulce.pulcebackend.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ItemService {

    public List<Item> search(String name, String type){
        if( name != null && type != null){
            return itemRepository.findByNameContainingIgnoreCaseAndType(name, type);
        }
        if (name != null){
            return itemRepository.findByNameContainingIgnoreCase(name);
        }
        if (type != null){
            return itemRepository.findByType(type);
        }
        return itemRepository.findAll();
    }

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item create(ItemDTO dto) {
        Item item = new Item(
                dto.getName(),
                dto.getOriginalPrice(),
                dto.getSellingPrice(),
                dto.getType(),
                dto.getExpirationDate(),
                dto.getAmount()
        );
        return itemRepository.save(item);
    }

    public Item update(int id, ItemDTO dto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        item.setName(dto.getName());
        item.setOriginalPrice(dto.getOriginalPrice());
        item.setSellingPrice(dto.getSellingPrice());
        item.setType(dto.getType());
        item.setExpirationDate(dto.getExpirationDate());
        item.setAmount(dto.getAmount());

        return itemRepository.save(item);
    }

    @Transactional
    public void delete(int id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        itemRepository.delete(item);
    }

    public List<Item> findAll(){
        return itemRepository.findAll();
    }

    public List<Item> findByExpirationInDays() {
        return itemRepository.findByExpirationInDays();
    }
}
