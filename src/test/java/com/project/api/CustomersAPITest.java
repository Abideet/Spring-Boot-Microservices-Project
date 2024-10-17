package com.project.api;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.api.CustomersAPI;
import com.project.domain.Customer;
import com.project.repository.CustomersRepository;

import java.util.Optional;
import java.util.Collections;
import java.util.Iterator;

public class CustomersAPITest {
    @Mock
    private CustomersRepository customersRepository;

    @InjectMocks
    private CustomersAPI customersAPI;

    private MockMvc mockMvc;

    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customersAPI).build();

        customer = new Customer();
        customer.setId(1L);
        customer.setName("Abideet");
        customer.setEmail("Abideet@mail.com");
    }

    @Test
    void testGetAllCustomers() throws Exception {
        when(customersRepository.findAll()).thenReturn(Collections.singletonList(customer));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Abideet"));

        verify(customersRepository, times(1)).findAll();
    }

    @Test
    void testGetCustomerByIdSuccess() throws Exception {
        when(customersRepository.findById(1L)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Abideet"));

        verify(customersRepository, times(1)).findById(1L);
    }

    @Test
    void testAddCustomerSuccess() throws Exception {
        Customer newCustomer = new Customer();
        newCustomer.setName("Tom");
        newCustomer.setEmail("tom@exampl.com");

        when(customersRepository.save(any(Customer.class))).thenReturn(newCustomer);

        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newCustomer)))
                .andExpect(status().isCreated());

        verify(customersRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testAddCustomerBadRequest() throws Exception {
        Customer invalidCustomer = new Customer();
        // Invalid because name and email are null

        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(invalidCustomer)))
                .andExpect(status().isBadRequest());

        verify(customersRepository, times(0)).save(any(Customer.class));
    }

    @Test
    void testUpdateCustomerSuccess() throws Exception {
        customer.setId(1L);
        customer.setName("Updated Name");

        when(customersRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customersRepository.save(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(put("/api/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk());

        verify(customersRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testUpdateCustomerBadRequest() throws Exception {
        Customer invalidCustomer = new Customer(); // Missing name or email

        mockMvc.perform(put("/api/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(invalidCustomer)))
                .andExpect(status().isBadRequest());

        verify(customersRepository, times(0)).save(any(Customer.class));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        doNothing().when(customersRepository).deleteById(1L);

        mockMvc.perform(delete("/api/customers/1"))
                .andExpect(status().isOk());

        verify(customersRepository, times(1)).deleteById(1L);
    }

    @Test
    void testLookupCustomerByNameGetSuccess() throws Exception {
        when(customersRepository.findAll()).thenReturn(Collections.singletonList(customer));

        mockMvc.perform(get("/api/customers/byname/Abideet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Abideet"));

        verify(customersRepository, times(1)).findAll();
    }

    @Test
    void testLookupCustomerByNameGetNotFound() throws Exception {
        when(customersRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/customers/byname/UnknownName"))
                .andExpect(status().isBadRequest());

        verify(customersRepository, times(1)).findAll();
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
