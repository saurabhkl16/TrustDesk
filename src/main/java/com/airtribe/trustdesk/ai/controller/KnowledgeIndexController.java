
package com.airtribe.trustdesk.ai.controller;

import com.airtribe.trustdesk.ai.dto.KnowledgeSearchRequest;
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


    @PostMapping("/search")
    public ResponseEntity<?> searchKnowledge(
            @RequestBody KnowledgeSearchRequest request) {

        if (request.query() == null ||
                request.query().isBlank()) {

            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "status", "error",
                            "message", "Query cannot be empty"
                    ));
        }

        int topK = request.topK() == null
                ? 5
                : Math.max(1, Math.min(request.topK(), 20));

        var results = knowledgeVectorService.searchKnowledge(
                request.query(),
                topK
        );

        return ResponseEntity.ok(
                Map.of(
                        "status", "success",
                        "query", request.query(),
                        "resultCount", results.size(),
                        "results", results
                )
        );
    }

}
