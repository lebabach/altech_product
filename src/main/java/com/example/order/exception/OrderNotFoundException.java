package com.example.order.exception;

/**
 * @author bachlb
 */
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
