package com.example.infinispantask;

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class InfinispanConfiguration {

    @Bean("my-cache")
    org.infinispan.configuration.cache.Configuration configuration() {
        return new ConfigurationBuilder()
                .clustering()
                .cacheMode(CacheMode.LOCAL)
                .stateTransfer().awaitInitialTransfer(false)
                .build();
    }
}
