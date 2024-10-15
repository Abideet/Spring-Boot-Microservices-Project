package com.project.repository;

import org.springframework.data.repository.CrudRepository;

import com.project.domain.Customer;

public interface CustomersRepository extends CrudRepository<Customer, Long> {

}
