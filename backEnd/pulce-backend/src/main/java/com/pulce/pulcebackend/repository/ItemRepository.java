package com.pulce.pulcebackend.repository;

import com.pulce.pulcebackend.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item,Integer>
{
    Optional<Item> findByName(String name);

    boolean existsByName(String name);
}

