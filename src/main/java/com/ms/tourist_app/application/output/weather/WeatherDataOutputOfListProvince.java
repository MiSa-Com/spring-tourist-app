package com.ms.tourist_app.application.output.weather;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDataOutputOfListProvince {
    private Long idProvince;
    private String province;
    private String dateTime;
    private Float temp;
    private Float feelsLike;
    private Float tempMin;
    private Float tempMax;
    private Integer humidity;
    private String main;
    private String description;
}
