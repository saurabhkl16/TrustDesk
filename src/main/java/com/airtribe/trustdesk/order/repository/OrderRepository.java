
package com.airtribe.trustdesk.order.repository;

import com.airtribe.trustdesk.order.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository
        extends JpaRepository<CustomerOrder, String> {

    List<CustomerOrder> findByCustomer_CustomerId(String customerId);
}
