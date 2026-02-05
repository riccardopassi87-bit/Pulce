package com.pulce.pulcebackend.mapper;

import com.pulce.pulcebackend.dto.PizzaDTO;
import com.pulce.pulcebackend.dto.PizzaSearchDTO;
import com.pulce.pulcebackend.entity.Pizza;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PizzaMapper {
    @Mapping(target = "ingredients", ignore = true)
    @Mapping(target = "id", ignore = true)

    Pizza toEntity(PizzaDTO dto);

    PizzaSearchDTO toSearchDTO(Pizza pizza);
    List<PizzaSearchDTO> toSearchDTOlist(List<Pizza> pizzas);
}
