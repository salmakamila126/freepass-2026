package com.bcc.canteen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bcc.canteen.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
