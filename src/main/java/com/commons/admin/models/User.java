package com.commons.admin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;

@Entity
@Table(name = "users",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "username"),
           @UniqueConstraint(columnNames = "email")
       })
public class User {

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
	
	
//	@NotBlank
//	@Size(max = 20)
//	private String course;
	
	
	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@JsonIgnore
	@NotBlank
	@Size(max = 120)
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinTable(name = "user_payments",
//				joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
//				inverseJoinColumns = { @JoinColumn(name = "payments_id", referencedColumnName = "paymentId")}
//			)
//	private Payments payments;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", nullable = false)
	@JsonIgnore
//	@JoinTable(name = "user_course", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
////	@JoinTable(name = "user_course",
////				joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
////				inverseJoinColumns = { @JoinColumn(name = "course_id", referencedColumnName = "courseId")}
////			)
	private Course course;
	

	public User() {
	}

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
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
	
//	public void setCourse(String course) {
//		this.course = course;
//	}
//
//	public String getCourse() {
//		return course;
//	}
	
	public void setCourse(Course course) {
		this.course = course;
	}
	public Course getCourse() {
		return course;
	}
	
}
