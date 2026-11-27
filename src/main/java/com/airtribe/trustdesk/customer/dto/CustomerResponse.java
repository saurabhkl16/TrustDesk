package com.airtribe.trustdesk.customer.dto;

import java.time.LocalDate;
import java.util.Set;

public record CustomerResponse(
        String customerId,
        String name,
        String email,
        String tier,
        String country,
        LocalDate createdAt,
        boolean verified,
        Set<String> tags
) {
}
