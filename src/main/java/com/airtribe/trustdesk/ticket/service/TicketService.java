
package com.airtribe.trustdesk.ticket.service;

import com.airtribe.trustdesk.customer.entity.Customer;
import com.airtribe.trustdesk.order.dto.OrderItemResponse;
import com.airtribe.trustdesk.order.entity.CustomerOrder;
import com.airtribe.trustdesk.ticket.dto.TicketResponse;
import com.airtribe.trustdesk.ticket.entity.Ticket;
import com.airtribe.trustdesk.ticket.repository.TicketRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional(readOnly = true)
    public List<TicketResponse> getAllTickets() {

        return ticketRepository.findAllWithContext()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TicketResponse getTicketById(String ticketId) {

        Ticket ticket = ticketRepository
                .findWithContextByTicketId(ticketId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Ticket not found: " + ticketId
                ));

        return mapToResponse(ticket);
    }

    private TicketResponse mapToResponse(Ticket ticket) {

        Customer customer = ticket.getCustomer();
        CustomerOrder order = ticket.getOrder();

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

        TicketResponse.CustomerContext customerContext =
                new TicketResponse.CustomerContext(
                        customer.getCustomerId(),
                        customer.getName(),
                        customer.getEmail(),
                        customer.getTier(),
                        customer.getCountry(),
                        customer.isVerified()
                );

        TicketResponse.OrderContext orderContext =
                new TicketResponse.OrderContext(
                        order.getOrderId(),
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

        return new TicketResponse(
                ticket.getTicketId(),
                ticket.getChannel(),
                ticket.getSubject(),
                ticket.getBody(),
                ticket.getCreatedAt(),
                ticket.getStatus(),
                customerContext,
                orderContext
        );
    }
}
