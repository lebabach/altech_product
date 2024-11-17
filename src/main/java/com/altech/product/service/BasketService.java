package com.altech.product.service;

import com.altech.product.dto.BasketDTO;
import com.altech.product.dto.DiscountDTO;
import com.altech.product.dto.ProductDTO;
import com.altech.product.mapper.BasketMapper;
import com.altech.product.model.Basket;
import com.altech.product.repository.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BasketService {

    @Autowired
    private ProductService productService;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private BasketMapper basketMapper;

    private Map<Long, Integer> basket = new HashMap<>();

    public void addProductToBasket(BasketDTO basketDTO) {
        Basket basketEntity = basketMapper.toEntity(basketDTO);
        basketRepository.save(basketEntity);
        basket.put(basketDTO.getProductId(), basketDTO.getQuantity());
    }

    public void removeProductFromBasket(BasketDTO basketDTO) {
        basketRepository.deleteById(basketDTO.getProductId());
        basket.remove(basketDTO.getProductId());
    }

    public String calculateReceipt() {
        double total = 0.0;
        StringBuilder receipt = new StringBuilder("Receipt:\n");

        for (Map.Entry<Long, Integer> entry : basket.entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();
            ProductDTO product = productService.getProductById(productId);
            DiscountDTO discount = productService.getDiscountByProductId(productId);

            double price = product.getPrice();
            double discountAmount = 0.0;

            if (discount != null) {
                discountAmount = applyDiscount(price, quantity, discount);
            }

            double finalPrice = (price * quantity) - discountAmount;
            total += finalPrice;

            receipt.append(product.getName())
                    .append(" x ")
                    .append(quantity)
                    .append(" = $")
                    .append(finalPrice)
                    .append("\n");
        }

        receipt.append("Total: $").append(total);
        return receipt.toString();
    }

    private double applyDiscount(double price, int quantity, DiscountDTO discount) {
        double discountAmount = 0.0;

        if ("Buy 1 get 50% off the second".equals(discount.getDescription())) {
            int discountedItems = quantity / 2;
            discountAmount = discountedItems * (price * discount.getDiscountPercentage() / 100);
        }

        return discountAmount;
    }
}