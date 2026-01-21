package com.pulce.pulcebackend.repository;

import com.pulce.pulcebackend.entity.Pizza;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

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
}

