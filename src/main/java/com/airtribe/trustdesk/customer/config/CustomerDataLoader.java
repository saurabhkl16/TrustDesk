
package com.airtribe.trustdesk.customer.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.airtribe.trustdesk.customer.entity.Customer;
import com.airtribe.trustdesk.customer.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;


import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Component
public class CustomerDataLoader implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;

    public CustomerDataLoader(
            CustomerRepository customerRepository,
            ObjectMapper objectMapper) {
        this.customerRepository = customerRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {

        ClassPathResource resource =
                new ClassPathResource("data/customers.json");

        try (InputStream inputStream = resource.getInputStream()) {

            List<CustomerSeedData> customers =
                    objectMapper.readValue(
                            inputStream,
                            new TypeReference<>() {}
                    );

            int importedCount = 0;

            for (CustomerSeedData data : customers) {

                if (customerRepository.existsById(data.customerId())) {
                    continue;
                }

                Customer customer = new Customer();

                customer.setCustomerId(data.customerId());
                customer.setName(data.name());
                customer.setEmail(data.email());
                customer.setTier(data.tier());
                customer.setCountry(data.country());
                customer.setCreatedAt(data.createdAt());
                customer.setVerified(data.verified());
                customer.setTags(
                        new HashSet<>(data.tags())
                );

                customerRepository.save(customer);
                importedCount++;
            }

            System.out.println(
                    "Customer seed import completed. New records: "
                            + importedCount
            );
        }
    }

    private record CustomerSeedData(

            @JsonProperty("customer_id")
            String customerId,

            String name,

            String email,

            String tier,

            String country,

            @JsonProperty("created_at")
            LocalDate createdAt,

            boolean verified,

            List<String> tags
    ) {}
}
