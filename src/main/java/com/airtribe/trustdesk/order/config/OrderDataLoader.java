
package com.airtribe.trustdesk.order.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import com.airtribe.trustdesk.customer.entity.Customer;
import com.airtribe.trustdesk.customer.repository.CustomerRepository;
import com.airtribe.trustdesk.order.entity.CustomerOrder;
import com.airtribe.trustdesk.order.entity.OrderItem;
import com.airtribe.trustdesk.order.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(2)
public class OrderDataLoader implements CommandLineRunner {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;

    public OrderDataLoader(
            OrderRepository orderRepository,
            CustomerRepository customerRepository,
            ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {

        ClassPathResource resource =
                new ClassPathResource("data/orders.json");

        try (InputStream inputStream = resource.getInputStream()) {

            List<OrderSeedData> orders = objectMapper.readValue(
                    inputStream,
                    new TypeReference<>() {}
            );

            int importedCount = 0;

            for (OrderSeedData data : orders) {

                if (orderRepository.existsById(data.orderId())) {
                    continue;
                }

                Customer customer = customerRepository
                        .findById(data.customerId())
                        .orElseThrow(() -> new IllegalStateException(
                                "Customer not found: " + data.customerId()
                        ));

                CustomerOrder order = new CustomerOrder();

                order.setOrderId(data.orderId());
                order.setCustomer(customer);
                order.setStatus(data.status());
                order.setPlacedAt(data.placedAt());
                order.setDeliveredAt(data.deliveredAt());
                order.setEligibleReturnUntil(data.eligibleReturnUntil());
                order.setTotal(data.total());
                order.setCurrency(data.currency());
                order.setPaymentStatus(data.paymentStatus());
                order.setTrackingNumber(data.trackingNumber());

                List<OrderItem> items = new ArrayList<>();

                for (OrderItemSeedData itemData : data.items()) {
                    OrderItem item = new OrderItem();
                    item.setSku(itemData.sku());
                    item.setName(itemData.name());
                    item.setQuantity(itemData.quantity());
                    item.setCategory(itemData.category());
                    item.setFinalSale(itemData.finalSale());
                    items.add(item);
                }

                order.setItems(items);
                orderRepository.save(order);
                importedCount++;
            }

            System.out.println(
                    "Order seed import completed. New records: "
                            + importedCount
            );
        }
    }

    private record OrderSeedData(
            @JsonProperty("order_id")
            String orderId,

            @JsonProperty("customer_id")
            String customerId,

            String status,

            @JsonProperty("placed_at")
            LocalDate placedAt,

            @JsonProperty("delivered_at")
            LocalDate deliveredAt,

            @JsonProperty("eligible_return_until")
            LocalDate eligibleReturnUntil,

            BigDecimal total,

            String currency,

            @JsonProperty("payment_status")
            String paymentStatus,

            @JsonProperty("tracking_number")
            String trackingNumber,

            List<OrderItemSeedData> items
    ) {}

    private record OrderItemSeedData(
            String sku,
            String name,
            Integer quantity,
            String category,

            @JsonProperty("final_sale")
            boolean finalSale
    ) {}
}
