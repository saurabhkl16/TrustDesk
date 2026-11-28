
package com.airtribe.trustdesk.ai.dto;

public record RAGQuestionRequest(
        String question,
        Integer topK
) {
}
