package com.ms.tourist_app.application.utils;

import com.ms.tourist_app.application.service.WeatherApiService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyScheduler {

    private final WeatherApiService weatherApiService;

    public MyScheduler(WeatherApiService weatherApiService) {
        this.weatherApiService = weatherApiService;
    }

    @Scheduled(cron = "0 0 6,12,18 * * *")
    public void runJobAt6() throws IOException {
        weatherApiService.chargeCurrentWeatherIntoDatabase();
    }

    @Scheduled(cron = "0 30 6,12,18 * * *")
    public void runJob() throws IOException {
        weatherApiService.chargeWeatherForeCastIntoDatabase();
    }

}
