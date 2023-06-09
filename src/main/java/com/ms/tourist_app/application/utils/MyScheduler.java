package com.ms.tourist_app.application.utils;

import com.ms.tourist_app.application.service.WeatherService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyScheduler {

    private final WeatherService weatherService;

    public MyScheduler(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Scheduled(cron = "0 0 0,3,6,9,12,15,18,21 * * *")
    public void runJobAt6() throws IOException {
        weatherService.chargeCurrentWeatherIntoDatabase();
    }

    @Scheduled(cron = "0 10 0,3,6,9,12,15,18,21 * * *")
    public void runJob() throws IOException {
        weatherService.chargeWeatherForeCastIntoDatabase();
    }

}
