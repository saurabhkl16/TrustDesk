
package com.airtribe.trustdesk.order.dto;

public record OrderItemResponse(
        String sku,
        String name,
        Integer quantity,
        String category,
        boolean finalSale
) {
}
