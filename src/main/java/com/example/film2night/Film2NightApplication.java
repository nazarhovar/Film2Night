package com.example.film2night;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Film2NightApplication {
    public static void main(String[] args) {
        SpringApplication.run(Film2NightApplication.class, args);
    }
}
