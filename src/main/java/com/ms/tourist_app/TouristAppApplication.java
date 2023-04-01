package com.ms.tourist_app;

import com.github.slugify.Slugify;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TouristAppApplication {
    @Bean
    public Slugify slugify() {
        return new Slugify();
    }

    public static void main(String[] args) {
        SpringApplication.run(TouristAppApplication.class,args);
    }
}
