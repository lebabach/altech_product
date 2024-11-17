// src/main/java/com/altech/product/mapper/BasketMapper.java
package com.altech.product.mapper;

import com.altech.product.dto.BasketDTO;
import com.altech.product.model.Basket;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BasketMapper {
    Basket toEntity(BasketDTO basketDTO);
    BasketDTO toDto(Basket basket);
}