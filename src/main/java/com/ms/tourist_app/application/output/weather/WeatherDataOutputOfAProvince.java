package com.ms.tourist_app.application.output.weather;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDataOutputOfAProvince {
    private Long idProvince;
    private String nameProvince;
    private CurrentWeather currentWeather;
    private List<WeatherInformation> weatherInformationList;

    public WeatherDataOutputOfAProvince(Long idProvince, String nameProvince) {
        this.idProvince = idProvince;
        this.nameProvince = nameProvince;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CurrentWeather{
        private Float temp;
        private Float feelsLike;
        private Float tempMin;
        private Float tempMax;
        private Integer humidity;
        private String main;
        private String description;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeatherInformation{
        private String dateTime;
        private Float temp;
        private Float feelsLike;
        private Float tempMin;
        private Float tempMax;
        private Integer humidity;
        private String main;
        private String description;
    }
}
