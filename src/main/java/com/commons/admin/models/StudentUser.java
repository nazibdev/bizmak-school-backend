package com.commons.admin.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

//@Entity
//@Table(name = "students",
//       uniqueConstraints = {
//           @UniqueConstraint(columnNames = ""),
//           @UniqueConstraint(columnNames = "")
//       })
public class StudentUser {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 20)
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@NotBlank
	@Size(max = 20)
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	
	@NotBlank
	@Size(max = 20)
	private String course;
	
	
	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "user_payments",
				joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
				inverseJoinColumns = { @JoinColumn(name = "payments_id", referencedColumnName = "paymentId")}
			)
	private Payments payments;
	
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinTable(name = "user_course",
//				joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
//				inverseJoinColumns = { @JoinColumn(name = "course_id", referencedColumnName = "courseId")}
//			)
//	private Course course;
	

	public StudentUser() {
	}
}
