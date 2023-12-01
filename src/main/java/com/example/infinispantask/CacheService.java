package com.example.infinispantask;

import com.example.infinispantask.entities.Department;
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

    public Department getCache() {
        if (cache.containsKey(1L)) {
            return cache.get(1L);
        } else {
            Department newInfo = dao.getFullDepartmentInfo();
            cache.put(1L, newInfo);
            return newInfo;
        }
    }

    @Scheduled(fixedDelay = 20000)
    public void putCache() {
        Department oldInfo = cache.get(1L);
        if (oldInfo == null) {
            Department newInfo = dao.getFullDepartmentInfo();
            cache.put(1L, newInfo);
        } else {
            String newName = dao.getDepartmentName();
            if (!newName.equals(oldInfo.getDepartmentName())) {
                Department newInfo = dao.getFullDepartmentInfo();
                cache.put(1L, newInfo);
            }
        }
    }
}
