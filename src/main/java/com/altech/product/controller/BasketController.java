package com.altech.product.controller;

import com.altech.product.dto.BasketDTO;
import com.altech.product.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/basket")
public class BasketController {

    @Autowired
    private BasketService basketService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Void> addProductToBasket(@RequestBody BasketDTO basketDTO) {
        basketService.addProductToBasket(basketDTO);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/remove")
    public ResponseEntity<Void> removeProductFromBasket(@RequestBody BasketDTO basketDTO) {
        basketService.removeProductFromBasket(basketDTO);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/receipt")
    public ResponseEntity<String> calculateReceipt() {
        String receipt = basketService.calculateReceipt();
        return ResponseEntity.ok(receipt);
    }
}