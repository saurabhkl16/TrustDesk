
package com.airtribe.trustdesk.knowledge.controller;

import com.airtribe.trustdesk.knowledge.dto.KnowledgeRequest;
import com.airtribe.trustdesk.knowledge.dto.KnowledgeResponse;
import com.airtribe.trustdesk.knowledge.service.KnowledgeService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/knowledge")
public class KnowledgeController {

    private final KnowledgeService knowledgeService;

    public KnowledgeController(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    // CREATE KNOWLEDGE DOCUMENT
    @PostMapping
    public ResponseEntity<KnowledgeResponse> createKnowledge(
            @Valid @RequestBody KnowledgeRequest request) {

        KnowledgeResponse response =
                knowledgeService.createKnowledge(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET ALL KNOWLEDGE DOCUMENTS
    @GetMapping
    public ResponseEntity<List<KnowledgeResponse>> getAllKnowledge() {

        return ResponseEntity.ok(
                knowledgeService.getAllKnowledge()
        );
    }

    // GET KNOWLEDGE BY DOCUMENT ID
    @GetMapping("/{docId}")
    public ResponseEntity<KnowledgeResponse> getKnowledgeById(
            @PathVariable String docId) {

        return ResponseEntity.ok(
                knowledgeService.getKnowledgeById(docId)
        );
    }

    // UPDATE KNOWLEDGE DOCUMENT
    @PutMapping("/{docId}")
    public ResponseEntity<KnowledgeResponse> updateKnowledge(
            @PathVariable String docId,
            @Valid @RequestBody KnowledgeRequest request) {

        return ResponseEntity.ok(
                knowledgeService.updateKnowledge(docId, request)
        );
    }

    // DELETE KNOWLEDGE DOCUMENT
    @DeleteMapping("/{docId}")
    public ResponseEntity<Void> deleteKnowledge(
            @PathVariable String docId) {

        knowledgeService.deleteKnowledge(docId);

        return ResponseEntity.noContent().build();
    }
}
