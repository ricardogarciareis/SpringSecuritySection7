package com.dzrrcreations.events;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEvents {
	
	Logger logger = Logger.getLogger(AuthenticationEvents.class.getName());
	
	@EventListener
	public void onSuccess(AuthenticationSuccessEvent successEvent) {
		
		String message = MessageFormat.format("Login successful for the user: {0} with password: {1}", 
													successEvent.getAuthentication().getName(), 
													successEvent.getAuthentication().getCredentials());
		logger.log(Level.WARNING, message); 
	}
	
	@EventListener
	public void onFailure(AbstractAuthenticationFailureEvent failureEvent) {
		
		String message = MessageFormat.format("Login failed for the user: {0} with password: {1} due to: {2}", 
													failureEvent.getAuthentication().getName(), 
													failureEvent.getAuthentication().getCredentials(),
													failureEvent.getException().getMessage());
		logger.log(Level.SEVERE, message); 
	}
	
}
