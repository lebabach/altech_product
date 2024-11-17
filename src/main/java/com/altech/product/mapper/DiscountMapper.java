package com.altech.product.mapper;

import com.altech.product.dto.DiscountDTO;
import com.altech.product.model.Discount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiscountMapper {
    Discount toEntity(DiscountDTO discountDTO);
    DiscountDTO toDto(Discount discount);
}