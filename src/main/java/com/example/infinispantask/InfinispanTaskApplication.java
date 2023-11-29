package com.example.infinispantask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InfinispanTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfinispanTaskApplication.class, args);
    }

}
