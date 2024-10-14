package com.project.api;

import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.project.domain.Customer;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")
public class CustomersAPI 
{
  private List<Customer> customers;

  public CustomersAPI() 
  {
      customers = new ArrayList<>();
      customers.add(new Customer(1, "abideet", "pass", "abideet@mail.com"));
      customers.add(new Customer(2, "tom", "pass", "tom@mail.com"));
      customers.add(new Customer(3, "harry", "pass", "harry@mail.com"));
  }

  @GetMapping("/customers")
  public String getCustomers() 
  {
      StringBuilder jsonBuilder = new StringBuilder();
      jsonBuilder.append("[");
      
      for (int i = 0; i < customers.size(); i++) {
          appendCustomerJson(jsonBuilder, customers.get(i));
          if (i < customers.size() - 1) {
              jsonBuilder.append(",");
          }
      }
      
      jsonBuilder.append("]");
      return jsonBuilder.toString();
  }

  @GetMapping("/customers/{id}")
  public String getCustomerById(@PathVariable int id) 
  {
      Optional<Customer> customer = customers.stream()
                                             .filter(c -> c.getId() == id)
                                             .findFirst();
      
      if (customer.isPresent()) 
      {
          StringBuilder jsonBuilder = new StringBuilder();
          appendCustomerJson(jsonBuilder, customer.get());
          return jsonBuilder.toString();
      } else {
          return "{\"error\": \"Invalid Customer\"}";
      }
  }

  private void appendCustomerJson(StringBuilder jsonBuilder, Customer customer) 
  {
      jsonBuilder.append("{");
      jsonBuilder.append("\"id\":").append(customer.getId()).append(",");
      jsonBuilder.append("\"username\":\"").append(escapeJson(customer.getName())).append("\",");
      jsonBuilder.append("\"password\":\"").append(escapeJson(customer.getPassword())).append("\",");
      jsonBuilder.append("\"email\":\"").append(escapeJson(customer.getEmail())).append("\"");
      jsonBuilder.append("}");
  }

  private String escapeJson(String input) {
      if (input == null) {
          return "";
      }
      return input.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\b", "\\b")
                  .replace("\f", "\\f")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
  }
}

