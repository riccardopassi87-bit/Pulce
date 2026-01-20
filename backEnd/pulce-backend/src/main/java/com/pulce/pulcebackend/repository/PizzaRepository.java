package com.pulce.pulcebackend.repository;

import com.pulce.pulcebackend.entity.Pizza;
import org.springframework.data.repository.CrudRepository;

public interface PizzaRepository extends CrudRepository<Pizza, Long> {}
