package com.pulce.pulcebackend.repository;

import com.pulce.pulcebackend.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    List<Ingredient> findByNameContainingIgnoreCase(String name);
    List<Ingredient> findByType(String type);
    List<Ingredient> findByNameContainingIgnoreCaseAndType(String name, String Type);
}