package com.project.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.project.domain.Customer;
import com.project.domain.CustomerBuilder;

class CustomerBuilderTest {

    private Customer customer;

    @BeforeEach
    void setUp() {
        // Setup a Customer object for testing
        customer = new Customer();
        customer.setId(1);
        customer.setName("Abideet");
        customer.setEmail("Abideet@mail.com");
        customer.setPassword("password123");
    }

    @Test
    void testGetCustomerFromJsonString() {
        // Arrange
        String jsonString = new JSONObject()
                .put("identifier", 1) // Use 'identifier' instead of 'id' if expected
                .put("fullName", "Abideet")
                .put("emailAddress", "Abideet@mail.com")
                .put("secretKey", "password123")
                .toString();

        // Act
        Customer result = CustomerBuilder.buildIndividualFromJson(jsonString);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Abideet", result.getName());
        assertEquals("Abideet@mail.com", result.getEmail());
        assertEquals("password123", result.getPassword());
    }

    @Test
    void testGetCustomerAsJsonString() {
        // Act
        String jsonString = CustomerBuilder.convertCustomerToJson(customer);

        // Assert
        assertNotNull(jsonString);
        JSONObject jsonObject = new JSONObject(jsonString);
        assertEquals(1, jsonObject.getInt("id"));
        assertEquals("Abideet", jsonObject.getString("name"));
        assertEquals("Abideet@mail.com", jsonObject.getString("email"));
        assertEquals("password123", jsonObject.getString("password"));
    }

    @Test
    void testInvalidJsonParsing() {
        // Arrange
        String invalidJsonString = "{\"invalid\":\"json\"}";

        // Act
        Customer result = CustomerBuilder.buildIndividualFromJson(invalidJsonString);

        // Assert
        assertNull(result, "The result should be null for invalid JSON structure");
    }
}
