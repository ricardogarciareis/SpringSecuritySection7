package com.dzrrcreations.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(name="email")
	private String username;
	@Column(name="pwd")
	private String password;
	@Column(name="role")
	private String authority;
	
	
	public long getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}
	
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	
	public String getAuthority() {return authority;}
	public void setAuthority(String authority) {this.authority = authority;}
	
}
