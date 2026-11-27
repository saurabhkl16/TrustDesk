
package com.airtribe.trustdesk.knowledge.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KnowledgeRequest {

    @NotBlank(message = "Document ID is required")
    private String docId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    private String sourcePath;

    private String version;

    private String audience;

    public KnowledgeRequest() {
    }

}
