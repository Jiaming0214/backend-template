package com.ming.service.impl;

import com.ming.entity.User;
import com.ming.mapper.UserMapper;
import com.ming.service.AuthorizeService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AuthorizeServiceImpl implements AuthorizeService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private MailSender mailSender;
    @Value("${spring.mail.username}")
    private String SENDER_FROM;
    @Resource
    private StringRedisTemplate template;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!StringUtils.hasText(username)) throw new UsernameNotFoundException("用户名不能为空");

        User userInDb = Optional.ofNullable(userMapper.findByUsernameOrEmail(username))
                .orElseThrow(() -> new UsernameNotFoundException("用户名或密码错误"));

        return org.springframework.security.core.userdetails.User
                .withUsername(userInDb.getUsername())
                .password(userInDb.getPassword())
                .roles("user")
                .build();
    }

    /**
     * 1.先生成对应的验证码
     * 2.发送验证码到指定邮箱中
     * 2-1.如果在3分钟内再次请求验证码，那么重置时间
     * 3.把邮箱和对应的验证码放入Redis中，并设置3min过期时间
     * 4.用户注册时再从Redis中取出键值对，进行比对
     */
    @Override
    public Boolean sendValidateEmail(String email, String sessionId) {
        String key = "email:" + sessionId + ":" + email;
        if(Boolean.TRUE.equals(template.hasKey(key))) {
            Long expireTime = Optional.ofNullable(template.getExpire(key)).orElse(0L);
            if(expireTime > 120) return Boolean.FALSE;
        }
        // 生成验证码
        Random random = new Random();
        int code = random.nextInt(899999) + 100000;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SENDER_FROM);
        message.setTo(email);
        message.setSubject("您的验证邮件");
        message.setText("您的验证码为：" + code);
        try {
            mailSender.send(message);
            template.opsForValue().set(key, String.valueOf(code));
            template.expire(key, 3, TimeUnit.MINUTES);
        }catch (MailException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
