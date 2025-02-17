package com.ming.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthorizeService extends UserDetailsService {
    Boolean sendValidateEmail(String email, String sessionId);
}
