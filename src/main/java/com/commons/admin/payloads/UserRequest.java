package com.commons.admin.payloads;

import java.util.Set;

public class UserRequest {
	
	private String firstName;
	private String lastName;
	private String course;
//	private String password;
	private Set<String> roles;
	
	public UserRequest() {
		// TODO Auto-generated constructor stub
	}
	
	
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getCourse() {
		return course;
	}
	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

}
