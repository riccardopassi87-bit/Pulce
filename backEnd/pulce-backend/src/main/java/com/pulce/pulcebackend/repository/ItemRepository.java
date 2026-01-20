package com.pulce.pulcebackend.repository;

import com.pulce.pulcebackend.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer>
{
    List<Item> findByNameContainingIgnoreCase(String name);
    List<Item> findByType(String type);
    List<Item> findByNameContainingIgnoreCaseAndType(String name, String type);
}

