package com.bcc.canteen.util;

import com.bcc.canteen.entity.User;
import com.bcc.canteen.entity.Role;

public class AuthUtil {

    public static void checkAdmin(User user) {
        if (user == null || !user.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("Unauthorized: Admin only");
        }
    }

    public static void checkOwner(User user) {
        if (user == null || !user.getRole().equals(Role.OWNER)) {
            throw new RuntimeException("Unauthorized: Owner only");
        }
    }

    public static void checkUser(User user) {
        if (user == null || !user.getRole().equals(Role.USER)) {
            throw new RuntimeException("Unauthorized: User only");
        }
    }
}
