package com.ms.tourist_app.application.input.weathers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetWeatherByCoordinateDataInput {
    private Double lon;
    private Double lat;
}
