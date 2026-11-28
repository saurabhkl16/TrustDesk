
package com.airtribe.trustdesk.ai.service;

import com.airtribe.trustdesk.knowledge.entity.KnowledgeDocument;
import com.airtribe.trustdesk.knowledge.repository.KnowledgeDocumentRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KnowledgeVectorService {

    private final VectorStore vectorStore;
    private final KnowledgeDocumentRepository knowledgeRepository;

    public KnowledgeVectorService(
            VectorStore vectorStore,
            KnowledgeDocumentRepository knowledgeRepository) {
        this.vectorStore = vectorStore;
        this.knowledgeRepository = knowledgeRepository;
    }

    public int indexAllKnowledgeDocuments() {

        List<KnowledgeDocument> documents =
                knowledgeRepository.findAll();

        for (KnowledgeDocument knowledge : documents) {
            indexDocument(knowledge);
        }

        return documents.size();
    }

    private void indexDocument(KnowledgeDocument knowledge) {

        String docId = knowledge.getDocId();

        // Remove previous vector for this knowledge document
        vectorStore.delete("docId == '" + docId + "'");

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("docId", docId);

        addIfNotNull(metadata, "title", knowledge.getTitle());
        addIfNotNull(metadata, "sourcePath", knowledge.getSourcePath());
        addIfNotNull(metadata, "version", knowledge.getVersion());
        addIfNotNull(metadata, "audience", knowledge.getAudience());

        Document document = new Document(
                knowledge.getContent(),
                metadata
        );

        vectorStore.add(List.of(document));
    }

    private void addIfNotNull(
            Map<String, Object> metadata,
            String key,
            Object value) {

        if (value != null) {
            metadata.put(key, value);
        }
    }


    public List<Map<String, Object>> searchKnowledge(
            String query, int topK) {

        SearchRequest searchRequest = SearchRequest.builder()
                .query(query)
                .topK(topK)
                .build();

        List<Document> results =
                vectorStore.similaritySearch(searchRequest);

        List<Map<String, Object>> response = new ArrayList<>();

        for (Document document : results) {

            Map<String, Object> result = new HashMap<>();

            result.put("content", document.getText());
            result.put("metadata", document.getMetadata());

            response.add(result);
        }

        return response;
    }

}
