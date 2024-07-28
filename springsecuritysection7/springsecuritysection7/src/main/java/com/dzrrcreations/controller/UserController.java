package com.dzrrcreations.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dzrrcreations.dto.CustomerDTO;
import com.dzrrcreations.model.Customer;
import com.dzrrcreations.repository.CustomerRepository;

@RestController
public class UserController {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
		try {
			System.out.println("Caught plain text password: " + customer.getPassword() + " for " + customer.getUsername());
			String hashedPwd = passwordEncoder.encode(customer.getPassword()); 
			customer.setPassword(hashedPwd);
			
			Customer savedCustomer = customerRepository.save(customer);
			if(savedCustomer.getId() > 0) {
				return ResponseEntity.status(HttpStatus.CREATED).body("Given user details are successfully registered");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User registration failed");
			}
		} catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An exception occured due to " + ex.getMessage());
		}
	}
	
	@PutMapping("/changepwd")
	public ResponseEntity<String> changeUserPwd(@RequestBody CustomerDTO customerDTO) {
		try {
			Optional<Customer> foundCustomerByUsername = customerRepository.findByUsername(customerDTO.getUsername());
			Boolean pwdMatches = passwordEncoder.matches(customerDTO.getOldpassword(), foundCustomerByUsername.get().getPassword());
			if(foundCustomerByUsername != null && pwdMatches) {
				System.out.println("Caught plain text password: " + customerDTO.getNewpassword() + " for " + customerDTO.getUsername());
				String hashedNewPwd = passwordEncoder.encode(customerDTO.getNewpassword()); 
				foundCustomerByUsername.get().setPassword(hashedNewPwd);
				Customer savedCustomer = customerRepository.save(foundCustomerByUsername.get());
				if(savedCustomer.getId() > 0) {
					return ResponseEntity.status(HttpStatus.CREATED).body("Password is successfully changed");
				} else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password changing failed");
				}
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password changing failed");
			}
		} catch(Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An exception occured due to " + ex.getMessage());
		}
	}
}
