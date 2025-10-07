package com.Portal.FeedbackPortal.dto;

import java.time.LocalDateTime;

public class FeedbackDTO {
	private String id;
	private String courseId;
	private String studentId;
	private String comments;
	private int rating;
	private LocalDateTime createdAt;

	public FeedbackDTO() {
	}

	public FeedbackDTO(String id, String courseId, String studentId, String comments, int rating,
			LocalDateTime createdAt) {
		this.id = id;
		this.courseId = courseId;
		this.studentId = studentId;
		this.comments = comments;
		this.rating = rating;
		this.createdAt = createdAt;
	}

	// Getters and setters

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
