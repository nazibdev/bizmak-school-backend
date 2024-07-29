package com.commons.admin.payloads;

import java.util.Set;

public class SignupRequest {
	
	private String fname;
	private String lname;
	private String email;
	private String course;
	private Set<String> role;
	private String username;
	private String password;
	
	
	
	
	
	public SignupRequest() {
		// TODO Auto-generated constructor stub
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}

	public Set<String> getRole() {
		// TODO Auto-generated method stub
		return role;
	}
	
	public void setRoles(Set<String> role) {
		this.role = role;
	}
	
	public void setFname(String fname) {
		this.fname = fname;
	}
	
	public String getFname() {
		return fname;
	}
	
	public void setLname(String lname) {
		this.lname = lname;
	}
	
	public String getLname() {
		return lname;
	}
	
	public void setCourse(String course) {
		this.course = course;
	}
	
	public String getCourse() {
		return course;
	}
	

}
