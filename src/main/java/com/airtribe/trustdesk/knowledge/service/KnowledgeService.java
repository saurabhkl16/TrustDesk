package com.airtribe.trustdesk.knowledge.service;

import com.airtribe.trustdesk.knowledge.dto.KnowledgeRequest;
import com.airtribe.trustdesk.knowledge.dto.KnowledgeResponse;
import com.airtribe.trustdesk.knowledge.entity.KnowledgeDocument;
import com.airtribe.trustdesk.knowledge.repository.KnowledgeDocumentRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class KnowledgeService {

    private final KnowledgeDocumentRepository repository;

    public KnowledgeService(KnowledgeDocumentRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @Transactional
    public KnowledgeResponse createKnowledge(KnowledgeRequest request) {

        if (repository.existsById(request.getDocId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Knowledge document already exists: " + request.getDocId()
            );
        }

        KnowledgeDocument document = new KnowledgeDocument();

        document.setDocId(request.getDocId());
        document.setTitle(request.getTitle());
        document.setContent(request.getContent());
        document.setSourcePath(request.getSourcePath());
        document.setVersion(request.getVersion());
        document.setAudience(request.getAudience());
        document.setUpdatedAt(LocalDateTime.now());

        KnowledgeDocument saved = repository.save(document);

        return mapToResponse(saved);
    }

    // GET ALL
    @Transactional(readOnly = true)
    public List<KnowledgeResponse> getAllKnowledge() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET BY ID
    @Transactional(readOnly = true)
    public KnowledgeResponse getKnowledgeById(String docId) {

        KnowledgeDocument document = findDocumentById(docId);

        return mapToResponse(document);
    }

    // UPDATE
    @Transactional
    public KnowledgeResponse updateKnowledge(
            String docId,
            KnowledgeRequest request) {

        KnowledgeDocument document = findDocumentById(docId);

        document.setTitle(request.getTitle());
        document.setContent(request.getContent());
        document.setSourcePath(request.getSourcePath());
        document.setVersion(request.getVersion());
        document.setAudience(request.getAudience());
        document.setUpdatedAt(LocalDateTime.now());

        KnowledgeDocument updated = repository.save(document);

        return mapToResponse(updated);
    }

    // DELETE
    @Transactional
    public void deleteKnowledge(String docId) {

        KnowledgeDocument document = findDocumentById(docId);

        repository.delete(document);
    }

    // FIND DOCUMENT
    private KnowledgeDocument findDocumentById(String docId) {

        return repository.findById(docId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Knowledge document not found: " + docId
                ));
    }

    // ENTITY TO DTO MAPPING
    private KnowledgeResponse mapToResponse(KnowledgeDocument document) {

        return new KnowledgeResponse(
                document.getDocId(),
                document.getTitle(),
                document.getContent(),
                document.getSourcePath(),
                document.getVersion(),
                document.getAudience(),
                document.getUpdatedAt()
        );
    }
}