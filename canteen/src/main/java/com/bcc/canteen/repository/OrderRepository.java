package com.bcc.canteen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bcc.canteen.entity.Order;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByCanteenId(Long canteenId);
}
