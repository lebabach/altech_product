package com.altech.product.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.altech.product.dto.DiscountDTO;
import com.altech.product.dto.ProductDTO;
import com.altech.product.mapper.DiscountMapper;
import com.altech.product.model.Discount;
import com.altech.product.model.Product;
import com.altech.product.repository.DiscountRepository;
import com.altech.product.repository.ProductRepository;
import com.altech.product.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Mock
    private DiscountMapper discountMapper;

    @Mock
    private DiscountRepository discountRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Product 1");
        productDTO.setPrice(100.0);

        Product product = new Product();
        product.setName("Product 1");
        product.setPrice(100.0);

        when(productMapper.toEntity(productDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productDTO);

        ProductDTO createdProduct = productService.createProduct(productDTO);

        assertNotNull(createdProduct);
        assertEquals("Product 1", createdProduct.getName());
        assertEquals(100.0, createdProduct.getPrice());
    }

    @Test
    public void testRemoveProduct() {
        Long productId = 1L;

        productService.removeProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }


    @Test
    public void testAddDiscount() {
        DiscountDTO discountDTO = new DiscountDTO();
        discountDTO.setProductId(1L);
        discountDTO.setDescription("Buy 1 get 50% off the second");
        discountDTO.setDiscountPercentage(50.0);

        Discount discount = new Discount();
        discount.setProductId(1L);
        discount.setDescription("Buy 1 get 50% off the second");
        discount.setDiscountPercentage(50.0);

        when(discountMapper.toEntity(discountDTO)).thenReturn(discount);
        when(discountRepository.save(discount)).thenReturn(discount);

        productService.addDiscount(discountDTO);

        verify(discountRepository, times(1)).save(discount);
    }
}