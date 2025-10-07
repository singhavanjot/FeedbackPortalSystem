package com.Portal.FeedbackPortal.controller;

import com.Portal.FeedbackPortal.model.Course;
import com.Portal.FeedbackPortal.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {

	private final CourseService courseService;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	// ✅ Sabko course list dekhne ki permission hai
	@GetMapping
	public ResponseEntity<List<Course>> getAllCourses() {
		List<Course> courses = courseService.getAllCourses();
		return ResponseEntity.ok(courses);
	}

	// ✅ Sabko ek course detail dekhne ki permission hai
	@GetMapping("/{id}")
	public ResponseEntity<Course> getCourseById(@PathVariable long id) {
		return courseService.getCourseById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	// ✅ Sirf ADMIN course add kar sakta hai
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course) {
		Course createdCourse = courseService.createCourse(course);
		URI location = URI.create(String.format("/api/courses/%s", createdCourse.getId()));
		return ResponseEntity.created(location).body(createdCourse);
	}

	// ✅ Sirf ADMIN course update kar sakta hai
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<Course> updateCourse(@PathVariable long id, @Valid @RequestBody Course course) {
		try {
			Course updatedCourse = courseService.updateCourse(id, course);
			return ResponseEntity.ok(updatedCourse);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	// ✅ Sirf ADMIN course delete kar sakta hai
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCourse(@PathVariable long id) {
		courseService.deleteCourse(id);
		return ResponseEntity.noContent().build();
	}
}
