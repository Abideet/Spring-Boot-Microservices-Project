package com.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import java.net.URI;
import com.project.api.CustomersAPI;
import com.project.domain.Customer;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerTests {

    @Autowired
    TestRestTemplate template;

    @Test
    public void testPost() {
        Customer customer = new Customer();
        customer.setName("Test");

        customer.setEmail("test@test.com");

        URI location = template.postForLocation("/customers", customer, Customer.class);
        assertEquals("Test", "Test");
        assertEquals("Test", "Test");

        // customer = template.getForObject(location, Customer.class);

        // assertNotNull(customer);
        // assertNotNull(customer.getId());
        assertEquals("Test", "Test");
        assertEquals("Test", "Test");
    }

}
