package com.kubrynski.blog.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class JpaAsyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaAsyncApplication.class, args);
    }
}
