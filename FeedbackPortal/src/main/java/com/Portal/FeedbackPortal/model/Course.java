package com.Portal.FeedbackPortal.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "courses")
public class Course {

	public static final String SEQUENCE_NAME = null;

	public Course() {
	}

	public Course(String courseCode, String courseName, String description) {
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.description = description;
	}

	@Id
	private Long id;

	@NotBlank(message = "Course code is mandatory")
	@Size(max = 10, message = "Course code must be less than 10 characters")
	private String courseCode;

	@NotBlank(message = "Course name is mandatory")
	@Size(max = 100, message = "Course name must be less than 100 characters")
	private String courseName;

	@Size(max = 500, message = "Description must be less than 500 characters")
	private String description;

	// Getters and setters

	public Long getId() {
		return id;
	}

	public void setId(long id) {
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
