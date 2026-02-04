package com.bcc.canteen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bcc.canteen.entity.Menu;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByCanteenId(Long canteenId);
    List<Menu> findByCanteenIdAndStockGreaterThan(Long canteenId, Integer stock);

}
