
package com.airtribe.trustdesk.knowledge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "knowledge_documents")
@Getter
@Setter
@NoArgsConstructor
public class KnowledgeDocument {

    @Id
    @Column(name = "doc_id", nullable = false)
    private String docId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "source_path")
    private String sourcePath;

    private String version;

    private String audience;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
