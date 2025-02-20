package com.ming.service;

import com.ming.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthorizeService extends UserDetailsService {
    String sendValidateEmail(String email, String sessionId);

    String validateAndRegister(UserDTO userDTO, String sessionId);
}
