package com.Portal.FeedbackPortal.service;

import com.Portal.FeedbackPortal.model.Course;
import com.Portal.FeedbackPortal.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
	private final CourseRepository courseRepository;
	private final SequenceGeneratorService sequenceGeneratorService;

	public CourseService(CourseRepository courseRepository, SequenceGeneratorService sequenceGeneratorService) {
		this.courseRepository = courseRepository;
		this.sequenceGeneratorService = sequenceGeneratorService;
	}

	public List<Course> getAllCourses() {
		return courseRepository.findAll();
	}

	public Optional<Course> getCourseById(long id) {
		return courseRepository.findById(id);
	}

	public Course createCourse(Course course) {
		course.setId(sequenceGeneratorService.generateSequence(Course.SEQUENCE_NAME));
		return courseRepository.save(course);
	}

	public Course updateCourse(long id, Course updatedCourse) {
		return courseRepository.findById(id).map(course -> {
			course.setCourseCode(updatedCourse.getCourseCode());
			course.setCourseName(updatedCourse.getCourseName());
			course.setDescription(updatedCourse.getDescription());
			return courseRepository.save(course);
		}).orElseThrow(() -> new RuntimeException("Course not found"));
	}

	public void deleteCourse(long id) {
		courseRepository.deleteById(id);
	}
}
