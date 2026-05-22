package com.ming.filter;

import com.ming.config.JWTConfiguration;
import com.ming.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JWTTokenFilter extends OncePerRequestFilter {
    private final JWTConfiguration jwtConfiguration;
    private final JWTUtil jwtUtil;

    private String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(jwtConfiguration.getHeader());
        if (bearerToken != null && bearerToken.startsWith(jwtConfiguration.getTokenPrefix())) {
            return bearerToken.substring(jwtConfiguration.getTokenPrefix().length());
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);

        if (token != null && jwtUtil.validateToken(token)) {
            Authentication auth = jwtUtil.getAuthenticationFromToken(request, token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            request.getSession().setAttribute("userId", jwtUtil.getUserIdFromToken(token));
        }

        filterChain.doFilter(request, response);
    }
}
