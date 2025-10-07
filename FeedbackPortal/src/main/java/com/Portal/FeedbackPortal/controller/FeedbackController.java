package com.Portal.FeedbackPortal.controller;

import com.Portal.FeedbackPortal.model.Feedback;
import com.Portal.FeedbackPortal.service.FeedbackService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "http://localhost:3000")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    // ✅ Only ADMIN can see all feedbacks
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Feedback> getAllFeedbacks() {
        return feedbackService.getAllFeedback();
    }

    // ✅ A USER can see only his own feedbacks
    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    public List<Feedback> getMyFeedbacks(Authentication auth) {
        return feedbackService.getFeedbackByStudentId(auth.getName());
    }

    // ✅ Both USER and ADMIN can add feedback
    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Feedback addFeedback(@RequestBody Feedback feedback, Authentication auth) {
        feedback.setStudentId(auth.getName()); // username as studentId
        return feedbackService.addFeedback(feedback);
    }
}
