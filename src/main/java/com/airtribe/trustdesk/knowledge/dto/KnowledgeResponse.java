
package com.airtribe.trustdesk.knowledge.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class KnowledgeResponse {

    private String docId;
    private String title;
    private String content;
    private String sourcePath;
    private String version;
    private String audience;
    private LocalDateTime updatedAt;

    public KnowledgeResponse() {
    }

    public KnowledgeResponse(
            String docId,
            String title,
            String content,
            String sourcePath,
            String version,
            String audience,
            LocalDateTime updatedAt) {

        this.docId = docId;
        this.title = title;
        this.content = content;
        this.sourcePath = sourcePath;
        this.version = version;
        this.audience = audience;
        this.updatedAt = updatedAt;
    }

}
