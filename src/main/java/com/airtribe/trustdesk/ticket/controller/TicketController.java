
package com.airtribe.trustdesk.ticket.controller;

import com.airtribe.trustdesk.ticket.dto.TicketResponse;
import com.airtribe.trustdesk.ticket.service.TicketService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<TicketResponse> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{ticketId}")
    public TicketResponse getTicketById(
            @PathVariable String ticketId
    ) {
        return ticketService.getTicketById(ticketId);
    }
}
