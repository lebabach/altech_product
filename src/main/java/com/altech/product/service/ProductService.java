package com.altech.product.service;

import com.altech.product.dto.ProductDTO;
import com.altech.product.dto.DiscountDTO;
import com.altech.product.exception.EntityNotFoundException;
import com.altech.product.mapper.ProductMapper;
import com.altech.product.mapper.DiscountMapper;
import com.altech.product.model.Product;
import com.altech.product.model.Discount;
import com.altech.product.repository.ProductRepository;
import com.altech.product.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private DiscountMapper discountMapper;

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        return productMapper.toDto(productRepository.save(product));
    }

    public void removeProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void addDiscount(DiscountDTO discountDTO) {
        Discount discount = discountMapper.toEntity(discountDTO);
        discountRepository.save(discount);
    }

    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    public DiscountDTO getDiscountByProductId(Long productId) {
        return discountRepository.findByProductId(productId)
                .map(discountMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Discount not found"));
    }
}