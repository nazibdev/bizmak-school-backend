package com.commons.admin.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "course" , uniqueConstraints = {
        @UniqueConstraint(columnNames = "course_name")
})
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id")
	private Long courseId;
	
	@NotBlank
	@Size(max = 50, min = 5)
	@Column(name = "course_name", nullable = false)
	private String courseName;
	
	@NotBlank
	@Size(max = 20, min = 2)
	@Column(name = "course_rate", nullable = false)
	private String courseRate;
	
	@NotBlank
	@Size(max = 50000, min = 10)
	@Column(name = "course_desc", nullable = false)
	private String courseDescription;
	
	@Column(name = "course_price", nullable = false)
	private Long coursePrice;
	
	@Column(name="show_course", nullable = false)
	private boolean showCourse = false;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id", fetch = FetchType.EAGER)
	private List<User> user;
	
	public Course() {
		// TODO Auto-generated constructor stub
	}
	
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseRate() {
		return courseRate;
	}
	public void setCourseRate(String courseRate) {
		this.courseRate = courseRate;
	}
	public String getCourseDescription() {
		return courseDescription;
	}
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}
	public void setCoursePrice(Long coursePrice) {
		this.coursePrice = coursePrice;
	}
	public Long getCoursePrice() {
		return coursePrice;
	}

	public boolean isShowCourse() {
		return showCourse;
	}

	public void setShowCourse(boolean showCourse) {
		this.showCourse = showCourse;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

	public List<User> getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", courseName=" + courseName + ", courseRate=" + courseRate
				+ ", courseDescription=" + courseDescription + ", coursePrice=" + coursePrice + ", showCourse="
				+ showCourse + ", user=" + user + "]";
	}
	
	
}
