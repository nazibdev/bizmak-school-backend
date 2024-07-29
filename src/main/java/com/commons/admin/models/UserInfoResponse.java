package com.commons.admin.models;

import java.util.List;

import org.springframework.http.ResponseCookie;

public class UserInfoResponse {

	private Long id;
	private String username;
	private String email;
	private List<String> roles;
	private String tokenValue;
	
	
	public UserInfoResponse(Long id, String username, String email, List<String> roles, ResponseCookie tokenValue) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
//		this.tokenValue = tokenValue;
	}
	
	public UserInfoResponse(Long id, String username, String email, List<String> roles, String tokenValue) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.tokenValue = tokenValue;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	public List<String> getRoles() {
		return roles;
	}
	
	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}
	
	public String getTokenValue() {
		return tokenValue;
	}

}
