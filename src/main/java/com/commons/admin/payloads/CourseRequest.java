package com.commons.admin.payloads;

public class CourseRequest {

	private String courseName;
	private String courseRate;
	private String courseDescription;
	private int coursePrice;
	private boolean showCourse;
	
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
	public void setCoursePrice(int coursePrice) {
		this.coursePrice = coursePrice;
	}
	public int getCoursePrice() {
		return coursePrice;
	}
	public boolean isShowCourse() {
		return showCourse;
	}

	public void setShowCourse(boolean showCourse) {
		this.showCourse = showCourse;
	}
	
}
