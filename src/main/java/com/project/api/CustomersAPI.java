package com.project.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.project.domain.Customer;
import com.project.repository.CustomersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/customers")
public class CustomersAPI {

    @Autowired
    CustomersRepository repo;

    public CustomersAPI() {

    }

    @GetMapping
    public Iterable<Customer> getAll() {
        // Iterable<Customer> customers = repo.findAll();
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Customer> getCustomer(@PathVariable long id) {
        return repo.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody Customer newCustomer) {
        if (newCustomer.getName() == null || newCustomer.getEmail() == null) {
            return ResponseEntity.badRequest().build();
        }
        newCustomer = repo.save(newCustomer);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCustomer.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putCustomer(@RequestBody Customer customer, @PathVariable long id) {
        if (customer.getId() != id || customer.getName() == null || customer.getEmail() == null) {
            return ResponseEntity.badRequest().build();
        }

        repo.save(customer);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable long id) {
        repo.deleteById(id);
    }
}
