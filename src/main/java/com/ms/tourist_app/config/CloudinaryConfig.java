package com.ms.tourist_app.config;

import com.cloudinary.Cloudinary;
import com.ms.tourist_app.application.constants.AppEnv;
import com.ms.tourist_app.application.constants.AppStr;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Configuration
@DependsOn("appEnv")
public class CloudinaryConfig {

    @Bean
    public Cloudinary config() {
        Map<String,String> config = new HashMap<>();
        config.put(AppStr.CloudImage.cloudName, AppEnv.cloudinaryCloudName);
        config.put(AppStr.CloudImage.apiKey, AppEnv.cloudinaryApiKey);
        config.put(AppStr.CloudImage.apiSecret, AppEnv.cloudinaryApiSecret);
        return new Cloudinary(config);
    }

}