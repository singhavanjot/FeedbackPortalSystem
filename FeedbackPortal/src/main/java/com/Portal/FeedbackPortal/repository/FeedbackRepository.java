package com.Portal.FeedbackPortal.repository;

import com.Portal.FeedbackPortal.model.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FeedbackRepository extends MongoRepository<Feedback, String> {
	List<Feedback> findByCourseId(String courseId);
	List<Feedback> findByStudentId(String studentId);

}
