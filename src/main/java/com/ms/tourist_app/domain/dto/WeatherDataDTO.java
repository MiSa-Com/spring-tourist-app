package com.ms.tourist_app.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDataDTO {
    private String province;
    private Float temp;
    private Float feelsLike;
    private Float tempMin;
    private Float tempMax;
    private Integer humidity;
    private String main;
    private String description;
}
