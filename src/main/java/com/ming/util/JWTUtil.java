package com.ming.util;

import com.ming.config.JWTConfiguration;
import com.ming.constant.RedisConstant;
import com.ming.exception.ServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@AllArgsConstructor
public class JWTUtil {

    private final JWTConfiguration jwtConfiguration;
    private final RedisTemplate<String, Object> redisTemplate;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtConfiguration.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    private Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            log.info("token解析失败：", e);
            return null;
        }
    }

    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    private boolean isInvalidToken(String uuid) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(RedisConstant.JWT_BLACK_LIST + uuid));
    }

    public boolean invaildToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            throw new ServiceException(500, "token解析失败");
        }
        Date now = new Date();
        String uuid = claims.getId();
        Date expiration = claims.getExpiration();
        // 防止删掉token时，token已经过期了
        long expiraTime = Math.max(expiration.getTime() - now.getTime(), 0);
        if (isInvalidToken(uuid)) return false;
        redisTemplate.opsForValue().set(RedisConstant.JWT_BLACK_LIST + uuid, "", expiraTime, TimeUnit.MILLISECONDS);
        return true;
    }

    // 生成JWT Token
    public String generateToken(Long userId, String username, UserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtConfiguration.getExpiration());
        Map<String, Object> claims = Map.of(
                "userInfo", userDetails,
                "userId", userId);

        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .claims(claims)
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSecretKey())
                .compact();
    }

    // 从Token获取用户ID信息
    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            throw new ServiceException(500, "token解析失败");
        }

        Long userId = claims.get("userId", Long.class);
        if (userId == null) {
            throw new ServiceException(500, "token解析失败，无法获取用户ID信息");
        }

        return userId;
    }

    // 从Token获取用户信息
    public UserDetails getUserInfoFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            throw new ServiceException(500, "token解析失败");
        }

        Map userInfo = claims.get("userInfo", Map.class);
        if (userInfo == null) {
            throw new ServiceException(500, "用户信息为空");
        }

        List<?> authorities = (List<?>) userInfo.get("authorities");
        String[] roles = authorities.stream()
                .map(authority -> {
                    Map<?, ?> roleMap = (Map<?, ?>) authority;
                    return roleMap.get("authority").toString().substring(5);
                })
                .toArray(String[]::new);

        return User.withUsername(userInfo.get("username").toString())
                .password("")
                .roles(roles)
                .build();
    }

    // 验证token
    public boolean validateToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            // 如果token解析失败，则返回false
            if (claims == null) return false;
            // 如果token已经失效，则返回false
            if (isInvalidToken(claims.getId())) return false;
            // 最后判断token是否还在生效
            return !isTokenExpired(claims);
        } catch (Exception e) {
            log.info("token验证失败：", e);
            return false;
        }
    }

    // 获取认证信息
    public Authentication getAuthenticationFromToken(HttpServletRequest request, String token) {
        UserDetails userDetails = getUserInfoFromToken(token);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication;
    }

}
