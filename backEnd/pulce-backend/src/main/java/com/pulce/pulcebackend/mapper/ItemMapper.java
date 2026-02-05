package com.pulce.pulcebackend.mapper;

import com.pulce.pulcebackend.dto.ItemDTO;
import com.pulce.pulcebackend.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    Item toEntity(ItemDTO dto);
    void updateEntityFromDTO(ItemDTO dto, @MappingTarget Item entity);
}
