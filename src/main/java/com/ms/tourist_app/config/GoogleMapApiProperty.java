package com.ms.tourist_app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "google")
public class GoogleMapApiProperty {
    private String api;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }
}
