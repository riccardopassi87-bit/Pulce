package com.pulce.pulcebackend.repository;

import com.pulce.pulcebackend.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    List<Ingredient> findByNameContainingIgnoreCase(String name);
    List<Ingredient> findByType(String type);
    List<Ingredient> findByNameContainingIgnoreCaseAndType(String name, String Type);
}