package com.commons.admin.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "payments")
public class Payments {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentId;
	
	@Column(name = "paid_balance")
	private int paidBalance;
	
	@Column(name = "remaining_balance")
	private int remainingBalance;
	
//	@OneToOne(mappedBy = "payments")
//	private User user;
	
	public Payments() {
		// TODO Auto-generated constructor stub
	}
	
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaidBalance(int paidBalance) {
		this.paidBalance = paidBalance;
	}
	public int getPaidBalance() {
		return paidBalance;
	}
	public void setRemainingBalance(int remainingBalance) {
		this.remainingBalance = remainingBalance;
	}
	public int getRemainingBalance() {
		return remainingBalance;
	}

}
