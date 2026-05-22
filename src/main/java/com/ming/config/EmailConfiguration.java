package com.ming.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class EmailConfiguration {
    @Value("${spring.mail.username}")
    private String senderFrom;
}
