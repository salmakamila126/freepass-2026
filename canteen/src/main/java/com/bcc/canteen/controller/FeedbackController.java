package com.bcc.canteen.controller;

import com.bcc.canteen.entity.Feedback;
import com.bcc.canteen.service.FeedbackService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/{orderId}")
    public Feedback giveFeedback(@PathVariable Long orderId,
                                 @RequestParam Long userId,
                                 @RequestParam int rating,
                                 @RequestParam String comment) {
        return feedbackService.giveFeedback(orderId, userId, rating, comment);
    }

    @DeleteMapping("/{id}")
    public void deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
    }
}
