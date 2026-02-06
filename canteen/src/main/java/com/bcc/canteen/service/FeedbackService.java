package com.bcc.canteen.service;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import com.bcc.canteen.entity.*;
import com.bcc.canteen.repository.*;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepo;
    private final OrderRepository orderRepo;

    public FeedbackService(FeedbackRepository feedbackRepo, OrderRepository orderRepo) {
        this.feedbackRepo = feedbackRepo;
        this.orderRepo = orderRepo;
    }

    public Feedback giveFeedback(Long orderId, Long userId, int rating, String comment) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("Not your order");
        }

        if (order.getStatus() != OrderStatus.COMPLETED) {
            throw new RuntimeException("Order not completed yet");
        }

        if (order.getFeedback() != null) {
            throw new RuntimeException("Feedback already given");
        }

        if (rating < 1 || rating > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        Feedback feedback = new Feedback();
        feedback.setRating(rating);
        feedback.setComment(comment);
        feedback.setCreatedAt(LocalDateTime.now());
        feedback.setOrder(order);

        order.setFeedback(feedback);

        return feedbackRepo.save(feedback);
    }

    public void deleteFeedbackByOwner(Long feedbackId, Long ownerId) {
        Feedback feedback = feedbackRepo.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        Order order = feedback.getOrder();

        if (order.getCanteen() == null || order.getCanteen().getOwner() == null) {
            throw new RuntimeException("Canteen owner not found");
        }

        Long canteenOwnerId = order.getCanteen().getOwner().getId();

        if (!canteenOwnerId.equals(ownerId)) {
            throw new RuntimeException("You can only delete feedback for your own canteen");
        }

        feedbackRepo.delete(feedback);
    }

    public void deleteFeedback(Long id) {
        feedbackRepo.deleteById(id);
    }
}
