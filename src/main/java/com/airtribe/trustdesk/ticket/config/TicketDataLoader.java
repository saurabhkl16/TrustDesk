
package com.airtribe.trustdesk.ticket.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import com.airtribe.trustdesk.customer.entity.Customer;
import com.airtribe.trustdesk.customer.repository.CustomerRepository;
import com.airtribe.trustdesk.order.entity.CustomerOrder;
import com.airtribe.trustdesk.order.repository.OrderRepository;
import com.airtribe.trustdesk.ticket.entity.Ticket;
import com.airtribe.trustdesk.ticket.repository.TicketRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.List;

@Component
@Order(3)
public class TicketDataLoader implements CommandLineRunner {

    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;

    public TicketDataLoader(
            TicketRepository ticketRepository,
            CustomerRepository customerRepository,
            OrderRepository orderRepository,
            ObjectMapper objectMapper) {

        this.ticketRepository = ticketRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {

        ClassPathResource resource =
                new ClassPathResource("data/tickets.json");

        try (InputStream inputStream = resource.getInputStream()) {

            List<TicketSeedData> tickets =
                    objectMapper.readValue(
                            inputStream,
                            new TypeReference<>() {}
                    );

            int importedCount = 0;

            for (TicketSeedData data : tickets) {

                if (ticketRepository.existsById(data.ticketId())) {
                    continue;
                }

                Customer customer = customerRepository
                        .findById(data.customerId())
                        .orElseThrow(() -> new IllegalStateException(
                                "Customer not found: " + data.customerId()
                        ));

                CustomerOrder order = orderRepository
                        .findById(data.orderId())
                        .orElseThrow(() -> new IllegalStateException(
                                "Order not found: " + data.orderId()
                        ));

                if (!order.getCustomer().getCustomerId()
                        .equals(customer.getCustomerId())) {
                    throw new IllegalStateException(
                            "Ticket customer and order customer do not match: "
                                    + data.ticketId()
                    );
                }

                Ticket ticket = new Ticket();

                ticket.setTicketId(data.ticketId());
                ticket.setCustomer(customer);
                ticket.setOrder(order);
                ticket.setChannel(data.channel());
                ticket.setSubject(data.subject());
                ticket.setBody(data.body());
                ticket.setCreatedAt(data.createdAt());
                ticket.setStatus(data.status());

                ticketRepository.save(ticket);

                importedCount++;
            }

            System.out.println(
                    "Ticket seed import completed. New records: "
                            + importedCount
            );
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record TicketSeedData(

            @JsonProperty("ticket_id")
            String ticketId,

            @JsonProperty("customer_id")
            String customerId,

            @JsonProperty("order_id")
            String orderId,

            String channel,

            String subject,

            String body,

            @JsonProperty("created_at")
            OffsetDateTime createdAt,

            String status

    ) {}
}
