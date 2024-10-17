package com.project.resources;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.project.domain.Customer;

public class CustomerTest {

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer(1L, "Abideet", "password123", "abideet@example.com");
    }

    @Test
    void testCustomerConstructor() {
        Customer newCustomer = new Customer(1L, "Tom", "password456", "tom@example.com");

        assertEquals(1L, newCustomer.getId());
        assertEquals("Tom", newCustomer.getName());
        assertEquals("password456", newCustomer.getPassword());
        assertEquals("tom@example.com", newCustomer.getEmail());
    }

    @Test
    void testNoArgsConstructor() {
        Customer newCustomer = new Customer(); // Using the no-args constructor

        assertNotNull(newCustomer);
        assertNull(newCustomer.getName()); // Default value for name should be null
        assertNull(newCustomer.getPassword());
        assertNull(newCustomer.getEmail());
        assertEquals(0, newCustomer.getId()); // Default long is 0
    }

    @Test
    void testGettersAndSetters() {
        // Set values
        customer.setName("Updated Name");
        customer.setPassword("newpassword123");
        customer.setEmail("updated.email@example.com");
        customer.setId(2L);

        // Assert values were set properly
        assertEquals(2L, customer.getId());
        assertEquals("Updated Name", customer.getName());
        assertEquals("newpassword123", customer.getPassword());
        assertEquals("updated.email@example.com", customer.getEmail());
    }

    @Test
    void testGetId() {
        assertEquals(1L, customer.getId());
    }

    @Test
    void testGetName() {
        assertEquals("Abideet", customer.getName());
    }

    @Test
    void testGetEmail() {
        assertEquals("abideet@example.com", customer.getEmail());
    }

    @Test
    void testGetPassword() {
        assertEquals("password123", customer.getPassword());
    }
}
