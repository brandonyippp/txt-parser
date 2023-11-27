package com.example.fundloader.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.fundloader.constants.ApplicationConstants.DAILY_LOADS_CACHE;
import static com.example.fundloader.constants.ApplicationConstants.WEEKLY_LOADS_CACHE;

@Configuration
@EnableCaching
public class LoadCacheConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(DAILY_LOADS_CACHE, WEEKLY_LOADS_CACHE);
    }
}