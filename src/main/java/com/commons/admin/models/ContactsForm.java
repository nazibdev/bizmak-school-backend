package com.commons.admin.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "contact_us")
public class ContactsForm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 30)
	@Column(name = "full_name", nullable = false)
	private String fullname;
	
	@NotBlank
	@Size(max = 30)
	@Column(name = "email_address", nullable = false)
	private String emailAddress;
	
	@NotBlank
	@Size(max = 11)
	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;
	
	@NotBlank
	@Size(max = 500, min = 20)
	@Column(name = "message", nullable = false)
	private String message;
	
	
	public ContactsForm() {
		// TODO Auto-generated constructor stub
	}
	
	
	public ContactsForm(String fullname, String emailAddress, String phoneNumber, String message) {
		this.fullname = fullname;
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
		this.message = message;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getFullname() {
		return fullname;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getMessage() {
		return message;
	}
}
