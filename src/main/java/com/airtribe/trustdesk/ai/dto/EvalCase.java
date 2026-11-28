
package com.airtribe.trustdesk.ai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record EvalCase(

        @JsonProperty("case_id")
        String caseId,

        @JsonProperty("ticket_id")
        String ticketId,

        String input,

        EvalExpected expected
) {

    public record EvalExpected(

            String category,

            String priority,

            @JsonProperty("must_cite_doc_ids")
            List<String> mustCiteDocIds,

            @JsonProperty("allowed_actions")
            List<String> allowedActions,

            @JsonProperty("disallowed_actions")
            List<String> disallowedActions,

            @JsonProperty("should_escalate")
            boolean shouldEscalate,

            @JsonProperty("answer_requirements")
            List<String> answerRequirements
    ) {}
}


//Ye DTO kya karega?
//        - JSONL file ke ek JSON object ko Java object mein convert karega.
//        - expected ke andar saari evaluation requirements store hongi.
//        - @JsonProperty JSON ke snake_case fields ko Java ke camelCase fields se map karega.