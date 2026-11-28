
package com.airtribe.trustdesk.ai.dto;

import java.util.List;

public record EvaluationResult(

        String caseId,

        boolean categoryCorrect,

        boolean priorityCorrect,

        boolean citationCoverage,

        boolean allowedActionsValid,

        boolean disallowedActionsBlocked,

        boolean escalationCorrect,

        List<String> missingRequirements,

        boolean passed

) {}


//================================= Notes =================================================
//Fields ka meaning:
//        - categoryCorrect – AI ne correct ticket category identify ki?
//        - priorityCorrect – Priority sahi hai?
//        - citationCoverage – Required KB document cite hua?
//        - allowedActionsValid – Allowed actions follow hue?
//        - disallowedActionsBlocked – Restricted actions block hue?
//        - escalationCorrect – Escalation ka decision sahi tha?
//        - missingRequirements – Answer mein kya missing hai?
//        - passed – Overall test pass hua ya fail?