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
@Profile("!prod")
public class ProjectSecurityConfig {
	
	/*
	 * Authorization configurations
	 */
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		
		String config = "b";
		String choose = "";
		
		switch(config) {
			case "a":
				choose = "Standard configuration";
				http.csrf(csrfConfig -> csrfConfig.disable())
					.authorizeHttpRequests(requests -> requests.anyRequest().authenticated())
					.formLogin(Customizer.withDefaults())
					.httpBasic(Customizer.withDefaults());
				break;
			case "b":
				choose = "Custom security configurations";
				
				http.sessionManagement(smc -> smc
						.sessionFixation(sfc -> sfc.changeSessionId()) // This is optional because this Strategy is choosed by default
						.invalidSessionUrl("/invalidSession") // Redirect in case of Session Timeout	
						.maximumSessions(1) // Concurrent Session Control
						.maxSessionsPreventsLogin(true)); // With true parameter it is not possible to create a second session
				
				http.requiresChannel(rcc -> rcc.anyRequest().requiresInsecure()) // Only accepts HTTP
					.csrf(csrfConfig -> csrfConfig.disable());
				
				http.authorizeHttpRequests(requests -> requests
		                .requestMatchers("/myAccount","/myBalance","/myLoans","/myCards").authenticated()
		                .requestMatchers("/favicon.ico", // Sometimes occurs a GET error on browser for the favicon.icon non-existence
		                				 "/error**","/login**","logout**",
		                				 "/denied","/invalidSession","/expiredSession",
		                				 "/notices","/contact",
		                				 "/register","/changepwd").permitAll());

		        http.formLogin(Customizer.withDefaults());
		        
		        //http.httpBasic(Customizer.withDefaults());
		        http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
		        
		        // Global configurations:
				//http.exceptionHandling(ehc -> ehc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
				//     Choose if you want a JSON error message or a page
				http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
				//http.exceptionHandling(ehc -> ehc.accessDeniedPage("/denied"));
				break;
		}
		System.out.println(choose);
		return http.build();
	}
	
	/*
	 * Authentication configurations
	 * 
	 * Use this option if you want to use default Spring Security tables.
	 * If you want to customize your tables then comment this bean method.
	 */
	/*
	@Bean
	UserDetailsService userDetailsService(DataSource dataSource) {
		return new JdbcUserDetailsManager(dataSource);
	}
	*/
	
	
	
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
