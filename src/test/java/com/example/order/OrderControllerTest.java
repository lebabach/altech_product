package com.example.order;

import com.example.order.controller.OrderController;
import com.example.order.dto.OrderDTO;
import com.example.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author bachlb
 */
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    private OrderDTO orderDTO;

    @BeforeEach
    public void setUp() {
        orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setCustomerName("John Doe");
        orderDTO.setProduct("Laptop");
        orderDTO.setQuantity(1);
        orderDTO.setPrice(999.99);
        orderDTO.setStatus("Shipped");
    }

    @Test
    public void testGetAllOrders() throws Exception {
        List<OrderDTO> orders = Arrays.asList(orderDTO);
        when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerName").value("John Doe"));
    }

    @Test
    public void testGetOrderById() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(orderDTO);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("John Doe"));
    }

    @Test
    public void testCreateOrder() throws Exception {
        when(orderService.createOrder(any(OrderDTO.class))).thenReturn(orderDTO);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerName\":\"John Doe\",\"product\":\"Laptop\",\"quantity\":1,\"price\":999.99,\"status\":\"Shipped\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("John Doe"));
    }

    @Test
    public void testUpdateOrder() throws Exception {
        when(orderService.updateOrder(eq(1L), any(OrderDTO.class))).thenReturn(orderDTO);

        mockMvc.perform(put("/api/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerName\":\"John Doe\",\"product\":\"Laptop\",\"quantity\":1,\"price\":999.99,\"status\":\"Shipped\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("John Doe"));
    }

    @Test
    public void testDeleteOrder() throws Exception {
        mockMvc.perform(delete("/api/orders/1"))
                .andExpect(status().isNoContent());
    }
}
