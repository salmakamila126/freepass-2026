package com.bcc.canteen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bcc.canteen.entity.Feedback;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByOrderCanteenId(Long canteenId);
}
