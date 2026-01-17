package com.pulce.pulcebackend.repository;

import com.pulce.pulcebackend.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    Optional<Ingredient> findByName(String name);

    boolean existsByName(String name);
}