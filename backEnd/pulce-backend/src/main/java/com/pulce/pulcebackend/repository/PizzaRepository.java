package com.pulce.pulcebackend.repository;

import com.pulce.pulcebackend.entity.Pizza;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    @EntityGraph(attributePaths = "ingredients")
    List<Pizza> findByNameContainingIgnoreCase(String name);

    @EntityGraph(attributePaths = "ingredients")
    List<Pizza> findByType(String type);

    @EntityGraph(attributePaths = "ingredients")
    List<Pizza> findByNameContainingIgnoreCaseAndType(String name, String type);

    @EntityGraph(attributePaths = "ingredients")
    List<Pizza> findAll();

    List<Pizza> findByIngredientsId(int ingredientId);

}

