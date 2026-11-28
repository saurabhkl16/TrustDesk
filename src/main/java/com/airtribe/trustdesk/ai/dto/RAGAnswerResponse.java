
package com.airtribe.trustdesk.ai.dto;

import java.util.List;

public record RAGAnswerResponse(
        String status,
        String answer,
        List<String> citations,
        int retrievedDocuments,
        boolean escalationRequired,
        String escalationReason
) {
}
