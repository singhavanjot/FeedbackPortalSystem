package com.Portal.FeedbackPortal.dto;

import com.Portal.FeedbackPortal.model.Course;
import com.Portal.FeedbackPortal.model.Feedback;

import java.util.List;
import java.util.stream.Collectors;

public class MapperUtil {

	// Course -> CourseDTO
	public static CourseDTO toCourseDTO(Course course) {
		return new CourseDTO(course.getId(), course.getCourseCode(), course.getCourseName(), course.getDescription());
	}

	// CourseDTO -> Course
	public static Course toCourseEntity(CourseDTO dto) {
		Course course = new Course();
		course.setId(dto.getId());
		course.setCourseCode(dto.getCourseCode());
		course.setCourseName(dto.getCourseName());
		course.setDescription(dto.getDescription());
		return course;
	}

	// Feedback -> FeedbackDTO
	public static FeedbackDTO toFeedbackDTO(Feedback feedback) {
		return new FeedbackDTO(feedback.getId(), feedback.getCourseId(), feedback.getStudentId(),
				feedback.getComments(), feedback.getRating(), feedback.getCreatedAt());
	}

	// FeedbackDTO -> Feedback
	public static Feedback toFeedbackEntity(FeedbackDTO dto) {
		Feedback feedback = new Feedback();
		feedback.setId(dto.getId());
		feedback.setCourseId(dto.getCourseId());
		feedback.setStudentId(dto.getStudentId());
		feedback.setComments(dto.getComments());
		feedback.setRating(dto.getRating());
		feedback.setCreatedAt(dto.getCreatedAt());
		return feedback;
	}

	// Convert list of Courses to DTOs
	public static List<CourseDTO> toCourseDTOList(List<Course> courses) {
		return courses.stream().map(MapperUtil::toCourseDTO).collect(Collectors.toList());
	}

	// Convert list of Feedback to DTOs
	public static List<FeedbackDTO> toFeedbackDTOList(List<Feedback> feedbacks) {
		return feedbacks.stream().map(MapperUtil::toFeedbackDTO).collect(Collectors.toList());
	}
}
