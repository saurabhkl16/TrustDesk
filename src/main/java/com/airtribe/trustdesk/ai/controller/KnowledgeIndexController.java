
package com.airtribe.trustdesk.ai.controller;

import com.airtribe.trustdesk.ai.service.KnowledgeVectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai/knowledge")
public class KnowledgeIndexController {

    private final KnowledgeVectorService knowledgeVectorService;

    public KnowledgeIndexController(
            KnowledgeVectorService knowledgeVectorService) {
        this.knowledgeVectorService = knowledgeVectorService;
    }

    @PostMapping("/index")
    public ResponseEntity<Map<String, Object>> indexKnowledge() {

        int indexedCount =
                knowledgeVectorService.indexAllKnowledgeDocuments();

        return ResponseEntity.ok(
                Map.of(
                        "status", "success",
                        "message", "Knowledge documents indexed successfully",
                        "indexedDocuments", indexedCount
                )
        );
    }
}
