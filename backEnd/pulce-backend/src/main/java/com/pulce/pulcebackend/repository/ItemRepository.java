package com.pulce.pulcebackend.repository;

import com.pulce.pulcebackend.entity.Ingredient;
import com.pulce.pulcebackend.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer>
{
    List<Item> findByNameContainingIgnoreCase(String name);
    List<Item> findByType(String type);
    List<Item> findByNameContainingIgnoreCaseAndType(String name, String type);

    @Query(value = "SELECT * FROM items WHERE expiration_date = DATE_ADD(CURDATE(), INTERVAL :days DAY)",
            nativeQuery = true)
    List<Item> findByExpirationInDays(@Param("days") int days);

    @Query(value = "SELECT * FROM items WHERE expiration_date " +
                    "BETWEEN DATE_ADD(CURDATE(), INTERVAL :lastDay DAY) " +
                    "AND DATE_ADD(CURDATE(), INTERVAL :firstDay DAY)",
                nativeQuery = true)
    List<Item> findByExpirationDate(@Param("lastDay")int lastDay,@Param("firstDay") int firstDay);
}

