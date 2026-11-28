
package com.airtribe.trustdesk.ai.controller;

import com.airtribe.trustdesk.ai.dto.TicketTriageResponse;
import com.airtribe.trustdesk.ai.service.TicketTriageService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/triage")
public class TicketTriageController {

    private final TicketTriageService ticketTriageService;

    public TicketTriageController(
            TicketTriageService ticketTriageService) {
        this.ticketTriageService = ticketTriageService;
    }

    public record TriageRequest(String customerMessage) {}

    @PostMapping("/analyze")
    public TicketTriageResponse analyzeTicket(
            @RequestBody TriageRequest request) {

        return ticketTriageService.analyzeTicket(
                request.customerMessage()
        );
    }
}
