package com.ms.tourist_app.config;

import com.cloudinary.Cloudinary;
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
        config.put(AppStr.CloudImage.cloudName, "none01");
        config.put(AppStr.CloudImage.apiKey, "975898585293148");
        config.put(AppStr.CloudImage.apiSecret, "JJqFPcPDDF08WM4hL-K4ysUbXzo");
        return new Cloudinary(config);
    }

}