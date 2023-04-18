package com.ms.tourist_app.domain.dto.weather_response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Main {

    @JsonProperty("temp")
    private Float temp;

    @JsonProperty("feels_like")
    private Float feelsLike;

    @JsonProperty("temp_min")
    private Float tempMin;

    @JsonProperty("temp_max")
    private Float tempMax;

    @JsonProperty("pressure")
    private Integer pressure;

    @JsonProperty("humidity")
    private Integer humidity;

    @JsonProperty("sea_level")
    private Integer seaLevel;

    @JsonProperty("grnd_level")
    private Integer grndLevel;

}
