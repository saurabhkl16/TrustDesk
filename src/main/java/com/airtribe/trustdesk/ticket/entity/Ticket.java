
package com.airtribe.trustdesk.ticket.entity;

import com.airtribe.trustdesk.customer.entity.Customer;
import com.airtribe.trustdesk.order.entity.CustomerOrder;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
public class Ticket {

    @Id
    @Column(name = "ticket_id", nullable = false)
    private String ticketId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private CustomerOrder order;

    private String channel;

    private String subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(
            name = "created_at",
            nullable = false,
            columnDefinition = "TIMESTAMP WITH TIME ZONE"
    )
    private OffsetDateTime createdAt;

    private String status;
}
