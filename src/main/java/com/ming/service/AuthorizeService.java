package com.ming.service;

import com.ming.dto.auth.UserDTO;
import com.ming.vo.auth.MenuVO;
import com.ming.vo.auth.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AuthorizeService extends UserDetailsService {
    void processLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException;

    String sendValidateEmail(String email, String sessionId, Boolean hasUser);

    String validateAndRegister(UserDTO userDTO, String sessionId);

    String validateOnly(UserDTO userDTO, String sessionId);

    Boolean resetPassword(String password, String email);
}
