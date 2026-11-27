
package com.airtribe.trustdesk.order.controller;

import com.airtribe.trustdesk.order.dto.OrderResponse;
import com.airtribe.trustdesk.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/{orderId}")
    public OrderResponse getOrderById(
            @PathVariable String orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/customers/{customerId}/orders")
    public List<OrderResponse> getOrdersByCustomerId(
            @PathVariable String customerId) {
        return orderService.getOrdersByCustomerId(customerId);
    }
}
