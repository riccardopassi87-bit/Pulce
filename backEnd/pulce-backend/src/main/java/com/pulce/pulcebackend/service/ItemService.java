package com.pulce.pulcebackend.service;

import com.pulce.pulcebackend.dto.ItemDTO;
import com.pulce.pulcebackend.entity.Item;
import com.pulce.pulcebackend.mapper.ItemMapper;
import com.pulce.pulcebackend.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ItemService {

    public List<Item> search(String name, String type){
        if( name != null && type != null){
            return itemRepository.findByNameContainingIgnoreCaseAndType(name, type);
        } else if (name != null){
            return itemRepository.findByNameContainingIgnoreCase(name);
        } else if (type != null){
            return itemRepository.findByType(type);
        }
        return itemRepository.findAll();
    }

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemService(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository; this.itemMapper = itemMapper;
    }

    public Item create(ItemDTO dto) {
        Item item = itemMapper.toEntity(dto);
        return itemRepository.save(item);
    }
    @Transactional
    public Item update(int id, ItemDTO dto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        itemMapper.updateEntityFromDTO(dto, item);

        return itemRepository.save(item);
    }

    @Transactional
    public void delete(int id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        itemRepository.delete(item);
    }

    // EXPIRATION LOGIC FOR PERSISTING LIST
    public List<Item> getUrgentExpiration(){
        return itemRepository.findByExpirationDate(1,7);
    }
    public List<Item> getWarningExpiration(){
        return itemRepository.findByExpirationDate(8,19);
    }
    public List<Item> getUpcomingExpiration(){
        return itemRepository.findByExpirationDate(20,30);
    }
}
