
package com.airtribe.trustdesk.ticket.repository;

import com.airtribe.trustdesk.ticket.entity.Ticket;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TicketRepository
        extends JpaRepository<Ticket, String> {

    @EntityGraph(attributePaths = {
            "customer",
            "order",
            "order.items"
    })
    @Query("SELECT t FROM Ticket t")
    List<Ticket> findAllWithContext();

    @EntityGraph(attributePaths = {
            "customer",
            "order",
            "order.items"
    })
    @Query("SELECT t FROM Ticket t WHERE t.ticketId = :ticketId")
    Optional<Ticket> findWithContextByTicketId(
            @Param("ticketId") String ticketId
    );
}
