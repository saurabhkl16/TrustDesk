
package com.airtribe.trustdesk.ai.controller;

import com.airtribe.trustdesk.ai.dto.EvalCase;
import com.airtribe.trustdesk.ai.service.EvalCaseLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ai/evaluation")
public class EvalCaseController {

    private final EvalCaseLoader evalCaseLoader;

    public EvalCaseController(EvalCaseLoader evalCaseLoader) {
        this.evalCaseLoader = evalCaseLoader;
    }

    @GetMapping("/cases")
    public List<EvalCase> getEvalCases() {
        return evalCaseLoader.loadEvalCases();
    }
}
