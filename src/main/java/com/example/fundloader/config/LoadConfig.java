package com.example.fundloader.config;

import com.example.fundloader.model.Load;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadConfig {

    @Bean
    public Class<Load> loadClass() {
        return Load.class;
    }
}