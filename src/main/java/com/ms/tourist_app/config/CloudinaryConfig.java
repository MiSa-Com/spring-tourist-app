package com.ms.tourist_app.config;

import com.cloudinary.Cloudinary;
import com.ms.tourist_app.application.constants.AppEnv;
import com.ms.tourist_app.application.constants.AppStr;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary config() {
        Map<String,String> config = new HashMap<>();
        config.put(AppStr.CloudImage.cloudName, AppEnv.Cloudinary.cloudName);
        config.put(AppStr.CloudImage.apiKey, AppEnv.Cloudinary.apiKey);
        config.put(AppStr.CloudImage.apiSecret, AppEnv.Cloudinary.apiSecret);
        return new Cloudinary(config);
    }

}