package com.example.infinispantask;

import com.example.infinispantask.entities.Department;
import lombok.SneakyThrows;
import org.infinispan.Cache;
import org.infinispan.commons.api.CacheContainerAdmin;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class CacheService {
    private final Cache<Long, Department> cache;

    private final Dao dao;

    public CacheService(Configuration configuration, Dao dao, CacheListener cacheListener) {
        GlobalConfigurationBuilder global = GlobalConfigurationBuilder.defaultClusteredBuilder();
        global.serialization()
                .marshaller(new org.infinispan.commons.marshall.JavaSerializationMarshaller())
                .allowList()
                .addRegexp(".*");
        DefaultCacheManager cacheManager = new DefaultCacheManager(global.build());
        this.cache = cacheManager.administration()
                .withFlags(CacheContainerAdmin.AdminFlag.VOLATILE)
                .getOrCreateCache("my-cache", configuration);
        this.dao = dao;
        this.cache.addListener(cacheListener);
    }

    @SneakyThrows
    public Department getWithSpring() {
        Thread.sleep(2000);
        if (cache.containsKey(1L)) {
            return cache.get(1L);
        } else {
            Department newInfo = dao.checkInfo();
            cache.put(1L, newInfo);
            return newInfo;
        }
    }

    public Department cachePut() {
        Department oldInfo = cache.get(1L);
        Department newInfo = dao.checkInfo();
        if (oldInfo == null) {
            cache.put(1L, newInfo);
        } else {
            if (!oldInfo.equals(newInfo)) {
                System.out.println("CHANGE!");
                cache.put(1L, newInfo);
            }
        }
        return newInfo;
    }

    @Scheduled(fixedDelay = 20000)
    public void checkCache() {
        cachePut();
    }

}
