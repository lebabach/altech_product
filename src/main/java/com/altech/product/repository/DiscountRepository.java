package com.altech.product.repository;

import com.altech.product.dto.DiscountDTO;
import com.altech.product.model.Discount;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DiscountRepository extends CrudRepository<Discount, Long> {
   Optional<Discount> findByProductId(Long productId);
}