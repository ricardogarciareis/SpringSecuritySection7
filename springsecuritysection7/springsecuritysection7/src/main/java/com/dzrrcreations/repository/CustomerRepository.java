package com.dzrrcreations.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.dzrrcreations.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	
	Optional<Customer> findByUsername(String username);
	Optional<Customer> findByPassword(String password);

}
