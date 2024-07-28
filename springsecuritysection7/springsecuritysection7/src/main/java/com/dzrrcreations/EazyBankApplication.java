package com.dzrrcreations;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.dzrrcreations.model.Customer;
import com.dzrrcreations.model.User;
import com.dzrrcreations.repository.CustomerRepository;
import com.dzrrcreations.repository.UserRepository;

@SpringBootApplication
@EnableWebSecurity //Optional for Spring Boot but mAndatory for Spring
@EnableJpaRepositories("com.dzrrcreations.repository") //Optional if repositories are all inside the main package
@EntityScan("com.dzrrcreations.model") //Optional if models are all inside the main package
public class EazyBankApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(EazyBankApplication.class, args);
	}
	
	 @Bean
	 @Profile("!prod")
	 CommandLineRunner testUserRepository(Environment env, UserRepository userRepo, CustomerRepository customerRepo) {
		return (args) -> {
			System.out.println("----------------------------------------------------------------------------------------------------------");
			System.out.println("Database URL .........: " + env.getProperty("spring.datasource.url"));
			System.out.println("Database Username ....: " + env.getProperty("spring.datasource.username"));
			System.out.println("Database Password ....: " + env.getProperty("spring.datasource.password"));
			System.out.println("----------------------------------------------------------------------------------------------------------");
			List<User> user = (List<User>) userRepo.findAll();
			user.stream().forEach(u -> System.out.println(u.getUsername() + " | " + u.getPassword() + " | " + u.getEnabled()));
			System.out.println("----------------------------------------------------------------------------------------------------------");
			List<Customer> customer = (List<Customer>) customerRepo.findAll();
			customer.stream().forEach(u -> System.out.println(u.getUsername() + " | " + u.getPassword() + " | " + u.getAuthority()));
			System.out.println("----------------------------------------------------------------------------------------------------------");
		};
	 }

}
