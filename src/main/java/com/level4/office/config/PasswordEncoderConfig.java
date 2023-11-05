package com.level4.office.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 암호화 방식
        return new BCryptPasswordEncoder();
    }
}
