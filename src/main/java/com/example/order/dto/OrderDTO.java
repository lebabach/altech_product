package com.example.order.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bachlb
 */
@Getter
@Setter
public class OrderDTO {
    private Long id;
    private String customerName;
    private String product;
    private int quantity;
    private double price;
    private String status;
}
