
package com.airtribe.trustdesk.ai.service;

import com.airtribe.trustdesk.ai.dto.RAGAnswerResponse;
import com.airtribe.trustdesk.ai.dto.SupportWorkflowResponse;
import com.airtribe.trustdesk.ai.dto.TicketTriageResponse;
import org.springframework.stereotype.Service;

@Service
public class SupportWorkflowService {

    private final TicketTriageService ticketTriageService;
    private final RAGAnswerService ragAnswerService;

    public SupportWorkflowService(
            TicketTriageService ticketTriageService,
            RAGAnswerService ragAnswerService) {

        this.ticketTriageService = ticketTriageService;
        this.ragAnswerService = ragAnswerService;
    }


    public SupportWorkflowResponse processCustomerMessage(
            String customerMessage,
            int topK) {

        // Step 1: Classify customer message
        TicketTriageResponse triage =
                ticketTriageService.analyzeTicket(customerMessage);

        // Step 2: Generate grounded answer and retrieve citations
        RAGAnswerResponse ragAnswer =
                ragAnswerService.generateAnswer(customerMessage, topK);

        // Step 3: Enforce triage escalation
        if (triage.escalationRequired()) {

            RAGAnswerResponse escalationResponse =
                    new RAGAnswerResponse(
                            "escalation_required",
                            "Your request requires review by a human support specialist.",
                            ragAnswer.citations(),
                            ragAnswer.retrievedDocuments(),
                            true,
                            triage.escalationReason()
                    );

            return new SupportWorkflowResponse(
                    triage,
                    escalationResponse
            );
        }

        // Step 4: Return normal workflow response
        return new SupportWorkflowResponse(
                triage,
                ragAnswer
        );
    }

}
