package com.dzrrcreations.configurations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dzrrcreations.model.Customer;
import com.dzrrcreations.repository.CustomerRepository;


@Service
public class EazyBankUserDetailsService implements UserDetailsService {
	
	@Autowired
	CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer = customerRepository.findByUsername(username)
								.orElseThrow(() -> new UsernameNotFoundException("User details not found for the user: " + username));
		
		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(customer.getAuthority()));

		return new User(customer.getUsername(), customer.getPassword(), authorities);
	}

}
