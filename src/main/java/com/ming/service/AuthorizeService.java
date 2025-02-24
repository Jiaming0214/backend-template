package com.ming.service;

import com.ming.dto.auth.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthorizeService extends UserDetailsService {
    String sendValidateEmail(String email, String sessionId, Boolean hasUser);

    String validateAndRegister(UserDTO userDTO, String sessionId);

    String validateOnly(UserDTO userDTO, String sessionId);

    Boolean resetPassword(String password, String email);
}
