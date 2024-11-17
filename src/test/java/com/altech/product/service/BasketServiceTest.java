package com.altech.product.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.altech.product.dto.BasketDTO;
import com.altech.product.dto.DiscountDTO;
import com.altech.product.dto.ProductDTO;
import com.altech.product.model.Basket;
import com.altech.product.repository.BasketRepository;
import com.altech.product.mapper.BasketMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

public class BasketServiceTest {
    @Mock
    private ProductService productService;

    @Mock
    private BasketRepository basketRepository;

    @Mock
    private BasketMapper basketMapper;

    @InjectMocks
    private BasketService basketService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProductToBasket() {
        BasketDTO basketDTO = new BasketDTO();
        basketDTO.setProductId(1L);
        basketDTO.setQuantity(2);

        Basket basket = new Basket();
        basket.setProductId(1L);
        basket.setQuantity(2);

        when(basketMapper.toEntity(basketDTO)).thenReturn(basket);
        when(basketRepository.save(basket)).thenReturn(basket);

        basketService.addProductToBasket(basketDTO);

        verify(basketRepository, times(1)).save(basket);
    }

    @Test
    public void testRemoveProductFromBasket() {
        BasketDTO basketDTO = new BasketDTO();
        basketDTO.setProductId(1L);

        basketService.removeProductFromBasket(basketDTO);

        verify(basketRepository, times(1)).deleteById(basketDTO.getProductId());
    }

    @Test
    public void testCalculateReceipt() {
        // Mock basket data
        Map<Long, Integer> basket = new HashMap<>();
        basket.put(1L, 2);
        basket.put(2L, 3);

        // Mock product data
        ProductDTO product1 = new ProductDTO();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setPrice(100.0);

        ProductDTO product2 = new ProductDTO();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setPrice(200.0);

        // Mock discount data
        DiscountDTO discount1 = new DiscountDTO();
        discount1.setProductId(1L);
        discount1.setDescription("Buy 1 get 50% off the second");
        discount1.setDiscountPercentage(50.0);

        // Mock service methods
        when(productService.getProductById(1L)).thenReturn(product1);
        when(productService.getProductById(2L)).thenReturn(product2);
        when(productService.getDiscountByProductId(1L)).thenReturn(discount1);
        when(productService.getDiscountByProductId(2L)).thenReturn(null);

        // Set basket in service
        basketService.addProductToBasket(new BasketDTO(1L, 2));
        basketService.addProductToBasket(new BasketDTO(2L, 3));

        // Calculate receipt
        String receipt = basketService.calculateReceipt();

        // Verify receipt content
        assertNotNull(receipt);
        assertTrue(receipt.contains("Product 1 x 2 = $150.0"));
        assertTrue(receipt.contains("Product 2 x 3 = $600.0"));
        assertTrue(receipt.contains("Total: $750.0"));
    }
}
