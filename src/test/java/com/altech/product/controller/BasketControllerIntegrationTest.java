package com.altech.product.controller;

import com.altech.product.dto.BasketDTO;
import com.altech.product.dto.DiscountDTO;
import com.altech.product.dto.ProductDTO;
import com.altech.product.repository.BasketRepository;
import com.altech.product.service.BasketService;
import com.altech.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BasketControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private BasketService basketService;

    @MockBean
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        basketRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testAddProductToBasketAsUser() throws Exception {
        BasketDTO basketDTO = new BasketDTO(1L, 2);

        mockMvc.perform(post("/api/basket/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\":1,\"quantity\":2}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAddProductToBasketAsAdmin() throws Exception {
        BasketDTO basketDTO = new BasketDTO(1L, 2);

        mockMvc.perform(post("/api/basket/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\":1,\"quantity\":2}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testRemoveProductFromBasketAsUser() throws Exception {
        BasketDTO basketDTO = new BasketDTO(1L, 2);
        basketService.addProductToBasket(basketDTO);

        mockMvc.perform(post("/api/basket/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testRemoveProductFromBasketAsAdmin() throws Exception {
        BasketDTO basketDTO = new BasketDTO(1L, 2);
        basketService.addProductToBasket(basketDTO);

        mockMvc.perform(post("/api/basket/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testCalculateReceiptAsUser() throws Exception {
        BasketDTO basketDTO1 = new BasketDTO(1L, 2);
        BasketDTO basketDTO2 = new BasketDTO(2L, 3);
        basketService.addProductToBasket(basketDTO1);
        basketService.addProductToBasket(basketDTO2);

        ProductDTO product1 = new ProductDTO();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setPrice(10.0);

        ProductDTO product2 = new ProductDTO();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setPrice(20.0);

        DiscountDTO discount1 = new DiscountDTO();
        discount1.setDescription("Buy 1 get 50% off the second");
        discount1.setDiscountPercentage(50.0);

        given(productService.getProductById(1L)).willReturn(product1);
        given(productService.getProductById(2L)).willReturn(product2);
        given(productService.getDiscountByProductId(1L)).willReturn(discount1);
        given(productService.getDiscountByProductId(2L)).willReturn(null);

        mockMvc.perform(get("/api/basket/receipt"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Total:")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCalculateReceiptAsAdmin() throws Exception {
        BasketDTO basketDTO1 = new BasketDTO(1L, 2);
        BasketDTO basketDTO2 = new BasketDTO(2L, 3);
        basketService.addProductToBasket(basketDTO1);
        basketService.addProductToBasket(basketDTO2);

        ProductDTO product1 = new ProductDTO();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setPrice(10.0);

        ProductDTO product2 = new ProductDTO();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setPrice(20.0);

        DiscountDTO discount1 = new DiscountDTO();
        discount1.setDescription("Buy 1 get 50% off the second");
        discount1.setDiscountPercentage(50.0);

        given(productService.getProductById(1L)).willReturn(product1);
        given(productService.getProductById(2L)).willReturn(product2);
        given(productService.getDiscountByProductId(1L)).willReturn(discount1);
        given(productService.getDiscountByProductId(2L)).willReturn(null);

        mockMvc.perform(get("/api/basket/receipt"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Total:")));
    }
}