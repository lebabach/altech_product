package com.altech.product.exception;

/**
 * @author bachlb
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
