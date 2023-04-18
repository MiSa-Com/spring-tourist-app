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
public class Sys {

    @JsonProperty("type")
    private Integer type;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("country")
    private String country;

    @JsonProperty("sunrise")
    private Integer sunrise;

    @JsonProperty("sunset")
    private Integer sunset;

}
