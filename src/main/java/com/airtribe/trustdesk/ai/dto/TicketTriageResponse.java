
package com.airtribe.trustdesk.ai.dto;

public record TicketTriageResponse(
        String category,
        String priority,
        boolean escalationRequired,
        String escalationReason
) {}
