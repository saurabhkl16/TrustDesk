
package com.airtribe.trustdesk.order.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record OrderResponse(
        String orderId,
        String customerId,
        String status,
        LocalDate placedAt,
        LocalDate deliveredAt,
        LocalDate eligibleReturnUntil,
        BigDecimal total,
        String currency,
        String paymentStatus,
        String trackingNumber,
        List<OrderItemResponse> items
) {
}
