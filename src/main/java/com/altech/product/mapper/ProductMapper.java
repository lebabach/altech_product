package com.altech.product.mapper;

import com.altech.product.dto.ProductDTO;
import com.altech.product.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductDTO productDTO);
    ProductDTO toDto(Product product);
}