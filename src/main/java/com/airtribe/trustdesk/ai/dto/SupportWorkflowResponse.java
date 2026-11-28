
package com.airtribe.trustdesk.ai.dto;

public record SupportWorkflowResponse(
        TicketTriageResponse triage,
        RAGAnswerResponse ragAnswer
) {}
