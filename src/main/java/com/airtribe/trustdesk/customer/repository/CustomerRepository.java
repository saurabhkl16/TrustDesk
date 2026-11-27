package com.airtribe.trustdesk.customer.repository;

import com.airtribe.trustdesk.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
