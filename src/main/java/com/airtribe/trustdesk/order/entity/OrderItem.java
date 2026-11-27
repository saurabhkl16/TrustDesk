
package com.airtribe.trustdesk.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

    @Column(name = "sku")
    private String sku;

    @Column(name = "item_name")
    private String name;

    private Integer quantity;

    private String category;

    @Column(name = "final_sale")
    private boolean finalSale;
}
