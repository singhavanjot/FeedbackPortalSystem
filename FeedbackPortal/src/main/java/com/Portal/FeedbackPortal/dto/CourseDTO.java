package com.Portal.FeedbackPortal.dto;

public class CourseDTO {
	private Long id;
	private String courseCode;
	private String courseName;
	private String description;

	public CourseDTO() {
	}

	public CourseDTO(Long id, String courseCode, String courseName, String description) {
		this.id = id;
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.description = description;
	}

	// Getters and setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
