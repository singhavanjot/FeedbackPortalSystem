package com.Portal.FeedbackPortal.service;

import com.Portal.FeedbackPortal.model.Feedback;
import com.Portal.FeedbackPortal.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

	private final FeedbackRepository feedbackRepository;

	public FeedbackService(FeedbackRepository feedbackRepository) {
		this.feedbackRepository = feedbackRepository;
	}

	public List<Feedback> getAllFeedback() {
		return feedbackRepository.findAll();
	}

	public List<Feedback> getFeedbackByCourseId(String courseId) {
		return feedbackRepository.findByCourseId(courseId);
	}

	public Optional<Feedback> getFeedbackById(String id) {
		return feedbackRepository.findById(id);
	}

	public Feedback addFeedback(Feedback feedback) {
		return feedbackRepository.save(feedback);
	}
	public List<Feedback> getFeedbackByStudentId(String studentId) {
	    return feedbackRepository.findByStudentId(studentId);
	}

	public void deleteFeedback(String id) {
		feedbackRepository.deleteById(id);
	}
}
