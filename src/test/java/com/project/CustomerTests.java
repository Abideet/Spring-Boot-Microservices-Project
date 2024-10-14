package com.project;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.api.CustomersAPI;



@SpringBootTest
public class CustomerTests 
{

    @Test
    public void testCustomerID() 
    {
        var api = new CustomersAPI();
        String result = api.getCustomerById(1);

        assertNotNull(result);
        assertTrue(result.contains("1"));
    }
    
}
