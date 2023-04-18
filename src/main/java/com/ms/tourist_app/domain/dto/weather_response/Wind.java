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
public class Wind {

    @JsonProperty("speed")
    private Float speed;

    @JsonProperty("deg")
    private Integer deg;

    @JsonProperty("gust")
    private Float gust;

}
