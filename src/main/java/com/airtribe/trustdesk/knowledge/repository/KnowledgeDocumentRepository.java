
package com.airtribe.trustdesk.knowledge.repository;

import com.airtribe.trustdesk.knowledge.entity.KnowledgeDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KnowledgeDocumentRepository
        extends JpaRepository<KnowledgeDocument, String> {
}
