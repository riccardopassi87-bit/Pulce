package com.pulce.pulcebackend.service;

import com.pulce.pulcebackend.ItemDTO;
import com.pulce.pulcebackend.entity.Item;
import com.pulce.pulcebackend.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service

public class ItemService {

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
}
