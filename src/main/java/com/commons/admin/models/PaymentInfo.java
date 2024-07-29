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
@Table(name = "payment_info_tbl")
public class PaymentInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 50)
	@Column(name = "amount", nullable = false)
	private String amount;
	
	@NotBlank
	@Size(max = 50)
	@Column(name = "email_address", nullable = false)
	private String emailAddress;
	
	@NotBlank
	@Size(max = 50)
	@Column(name = "course_name", nullable = false)
	private String courseName;
	
	@NotBlank
	@Size(max = 150, min = 10)
	@Column(name = "client_secret", nullable = false)
	private String clientSecret;
	
	public PaymentInfo() {
		// TODO Auto-generated constructor stub
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
//		clientSecret = clientSecret.split("_secret_")[0];
		this.clientSecret = clientSecret;
	}
	
	

}
