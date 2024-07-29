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
@Table(name = "subscriptions")
public class Subscriptions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subscriptions_id")
	private Long subscriptionsId;
	
	@NotBlank
	@Size(max = 50, min = 10)
	@Column(name = "subscriptions_email", nullable = false)
	private String subscriptionsEmail;
	
	public Subscriptions() {
		// TODO Auto-generated constructor stub
	}
	
	public void setSubscriptionsId(Long subscriptionsId) {
		this.subscriptionsId = subscriptionsId;
	}
	public Long getSubscriptionsId() {
		return subscriptionsId;
	}
	public void setSubscriptionsEmail(String subscriptionsEmail) {
		this.subscriptionsEmail = subscriptionsEmail;
	}
	public String getSubscriptionsEmail() {
		return subscriptionsEmail;
	}
}
