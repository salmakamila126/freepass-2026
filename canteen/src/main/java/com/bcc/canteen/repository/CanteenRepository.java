package com.bcc.canteen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bcc.canteen.entity.Canteen;
import java.util.List;
import java.util.Optional;

public interface CanteenRepository extends JpaRepository<Canteen, Long> {
    List<Canteen> findByOwnerId(Long ownerId);
    Optional<Canteen> findByIdAndOwnerId(Long id, Long ownerId);
}
