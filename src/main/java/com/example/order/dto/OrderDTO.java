package com.example.order.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
/**
 * @author bachlb
 */
@Getter
@Setter
public class OrderDTO {
    private Long id;

    @NotNull(message = "Customer ID is mandatory")
    private Long customerId;

    @NotBlank(message = "Product is mandatory")
    private String product;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @Positive(message = "Price must be positive")
    private double price;

    @NotBlank(message = "Status is mandatory")
    private String status;

    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
