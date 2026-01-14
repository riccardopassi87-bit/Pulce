package com.pulce.pulcebackend.service;

import com.pulce.pulcebackend.IngredientDTO;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {

    public void createIngredient(IngredientDTO ingredient) {
        System.out.println("Service Recived: " + ingredient);
    }
}
