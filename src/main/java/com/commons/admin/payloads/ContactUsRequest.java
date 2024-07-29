package com.commons.admin.payloads;

public class ContactUsRequest {
	
	private String fullname;
	private String emailAddress;
	private String phoneNumber;
	private String message;
	
	public ContactUsRequest() {
		// TODO Auto-generated constructor stub
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
