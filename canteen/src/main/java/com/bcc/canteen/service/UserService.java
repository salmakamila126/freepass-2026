package com.bcc.canteen.service;

import org.springframework.stereotype.Service;
import com.bcc.canteen.entity.User;
import com.bcc.canteen.dto.UserResponse;

@Service
public class UserService {

    public UserResponse mapToDTO(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}

