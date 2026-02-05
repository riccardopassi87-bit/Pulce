package com.pulce.pulcebackend.mapper;

import com.pulce.pulcebackend.dto.IngredientDTO;
import com.pulce.pulcebackend.entity.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    Ingredient toEntity(IngredientDTO dto);
    void updateEntityFromDTO(IngredientDTO dto, @MappingTarget Ingredient entity);
}
