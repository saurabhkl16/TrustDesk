
package com.airtribe.trustdesk.ai.controller;

import com.airtribe.trustdesk.ai.dto.SupportWorkflowResponse;
import com.airtribe.trustdesk.ai.service.SupportWorkflowService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/support")
public class SupportWorkflowController {

    private final SupportWorkflowService supportWorkflowService;

    public SupportWorkflowController(
            SupportWorkflowService supportWorkflowService) {
        this.supportWorkflowService = supportWorkflowService;
    }

    public record SupportRequest(
            String customerMessage,
            Integer topK
    ) {}

    @PostMapping("/process")
    public SupportWorkflowResponse processCustomerMessage(
            @RequestBody SupportRequest request) {

        int topK = request.topK() != null
                ? request.topK()
                : 5;

        return supportWorkflowService.processCustomerMessage(
                request.customerMessage(),
                topK
        );
    }
}
