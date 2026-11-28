
package com.airtribe.trustdesk.ai.dto;

public record KnowledgeSearchRequest(
        String query,
        Integer topK
) {
}
