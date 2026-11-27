package com.airtribe.trustdesk.ai.controller;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class EmbeddingTestController {

    private final EmbeddingModel embeddingModel;

    public EmbeddingTestController(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @GetMapping("/embedding-dimensions")
    public Map<String, Object> getEmbeddingDimensions() {

        int dimensions = embeddingModel.dimensions();

        return Map.of(
                "model", "nomic-embed-text",
                "dimensions", dimensions,
                "status", "success"
        );
    }
}