package com.Portal.FeedbackPortal.repository;

import com.Portal.FeedbackPortal.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<Course, Long> {
	// can declare custom finder methods if needed
}
