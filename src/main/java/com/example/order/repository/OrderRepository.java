package com.example.order.repository;

import com.example.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author bachlb
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}