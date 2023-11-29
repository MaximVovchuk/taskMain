package com.example.infinispantask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path ="/cache")
public class TestController {

    @Autowired
    private CacheService cacheService;

    @GetMapping
    public Object getCache() {
        return cacheService.getWithSpring();
    }

    @GetMapping("/update")
    public Object updateCache() {
        return cacheService.cachePut();
    }
}
