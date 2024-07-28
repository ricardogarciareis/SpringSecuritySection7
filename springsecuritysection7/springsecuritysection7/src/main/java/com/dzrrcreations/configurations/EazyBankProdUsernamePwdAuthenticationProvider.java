package com.dzrrcreations.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class EazyBankProdUsernamePwdAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		if(passwordEncoder.matches(password, userDetails.getPassword())) {
			// Fetch Age details and perform validation to check if Age >= 18
			return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
		} else {
			throw new BadCredentialsException("Invalid password");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		/*
		 * return copied from AbstractUserDetailsAuthenticationProvider.class
		 * Class extended by DaoAuthenticationProvider.class
		 * Because we want Username Password Authentication style (not OAuth2 or JAAS)
		 */
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
