package com.dzrrcreations.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import com.dzrrcreations.exceptions.CustomAccessDeniedHandler;
import com.dzrrcreations.exceptions.CustomBasicAuthenticationEntryPoint;

@Configuration
@Profile("prod")
public class ProjectSecurityProdConfig {
	
	/*
	 * Authorization configurations
	 */
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		
		System.out.println("Custom security configurations");
		
		http.sessionManagement(smc -> smc
				.sessionFixation(sfc -> sfc.changeSessionId())// This is optional because this Strategy is choosed by default
				.invalidSessionUrl("/invalidSession") // Redirect in case of session timeout
				.maximumSessions(3) // Concurrent Session Control
				.maxSessionsPreventsLogin(false) // With true parameter it is not possible to create a second session
				.expiredUrl("/expiredSession")); // Only for the user who's session is forcibly expired
		
		http.requiresChannel(rcc -> rcc.anyRequest().requiresSecure()) // Only accepts HTTPS
			.csrf(csrfConfig -> csrfConfig.disable());
		
		http.authorizeHttpRequests(requests -> requests
	            .requestMatchers("/myAccount","/myBalance","/myLoans","/myCards").authenticated()
	            .requestMatchers("/favicon.ico", // Sometimes occurs a GET error on browser for the favicon.icon non-existence
	            				 "/error**",
	            				 "/denied","/invalidSession",
	            				 "/notices","/contact",
	            				 "/register","/changepwd").permitAll());
		http.formLogin(Customizer.withDefaults())
	    	//.httpBasic(Customizer.withDefaults())
	    	.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
		
		// Global configurations:
		//http.exceptionHandling(ehc -> ehc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
		//     Choose if you want a JSON error message or a page
		http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
		//http.exceptionHandling(ehc -> ehc.accessDeniedPage("/denied"));
		
		return http.build();
	}

	/*
	 * Password encoding configuration
	 * https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/factory/PasswordEncoderFactories.html
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	/*
	 * Password checker
	 */
	@Bean
	CompromisedPasswordChecker compromisedPasswordChecker() {
		return new HaveIBeenPwnedRestApiPasswordChecker();
	}
}
