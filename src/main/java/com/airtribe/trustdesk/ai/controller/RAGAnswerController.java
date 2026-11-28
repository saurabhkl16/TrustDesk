
package com.airtribe.trustdesk.ai.controller;

import com.airtribe.trustdesk.ai.dto.RAGAnswerResponse;
import com.airtribe.trustdesk.ai.dto.RAGQuestionRequest;
import com.airtribe.trustdesk.ai.service.RAGAnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class RAGAnswerController {

    private final RAGAnswerService ragAnswerService;

    public RAGAnswerController(RAGAnswerService ragAnswerService) {
        this.ragAnswerService = ragAnswerService;
    }

    @PostMapping("/answer")
    public ResponseEntity<?> generateAnswer(
            @RequestBody RAGQuestionRequest request) {

        if (request.question() == null ||
                request.question().isBlank()) {

            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", "error",
                            "message", "Question cannot be empty"
                    )
            );
        }

        int topK = request.topK() == null
                ? 5
                : Math.max(1, Math.min(request.topK(), 20));

        RAGAnswerResponse response =
                ragAnswerService.generateAnswer(
                        request.question(), topK
                );

        return ResponseEntity.ok(response);
    }
}
