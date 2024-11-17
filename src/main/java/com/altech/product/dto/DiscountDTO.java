package com.altech.product.dto;

import lombok.Data;

@Data
public class DiscountDTO {
    private Long productId;
    private String description;
    private Double discountPercentage;
}
