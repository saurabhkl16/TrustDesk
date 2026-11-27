
package com.airtribe.trustdesk.order.service;

import com.airtribe.trustdesk.customer.repository.CustomerRepository;
import com.airtribe.trustdesk.order.dto.OrderItemResponse;
import com.airtribe.trustdesk.order.dto.OrderResponse;
import com.airtribe.trustdesk.order.entity.CustomerOrder;
import com.airtribe.trustdesk.order.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderService(
            OrderRepository orderRepository,
            CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrderById(String orderId) {
        CustomerOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Order not found: " + orderId
                ));

        return mapToResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByCustomerId(String customerId) {

        if (!customerRepository.existsById(customerId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Customer not found: " + customerId
            );
        }

        return orderRepository.findByCustomer_CustomerId(customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private OrderResponse mapToResponse(CustomerOrder order) {

        List<OrderItemResponse> items = order.getItems()
                .stream()
                .map(item -> new OrderItemResponse(
                        item.getSku(),
                        item.getName(),
                        item.getQuantity(),
                        item.getCategory(),
                        item.isFinalSale()
                ))
                .toList();

        return new OrderResponse(
                order.getOrderId(),
                order.getCustomer().getCustomerId(),
                order.getStatus(),
                order.getPlacedAt(),
                order.getDeliveredAt(),
                order.getEligibleReturnUntil(),
                order.getTotal(),
                order.getCurrency(),
                order.getPaymentStatus(),
                order.getTrackingNumber(),
                items
        );
    }
}
