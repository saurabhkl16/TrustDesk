
package com.airtribe.trustdesk.ai.service;

import com.airtribe.trustdesk.ai.dto.EvalCase;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class EvalCaseLoader {

    private final ObjectMapper objectMapper;

    public EvalCaseLoader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<EvalCase> loadEvalCases() {

        List<EvalCase> evalCases = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new ClassPathResource("data/eval_cases.jsonl")
                                .getInputStream(),
                        StandardCharsets.UTF_8
                ))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.isBlank()) {
                    continue;
                }

                EvalCase evalCase = objectMapper.readValue(
                        line,
                        EvalCase.class
                );

                evalCases.add(evalCase);
            }

        } catch (Exception exception) {
            throw new RuntimeException(
                    "Failed to load evaluation cases",
                    exception
            );
        }

        return evalCases;
    }
}
