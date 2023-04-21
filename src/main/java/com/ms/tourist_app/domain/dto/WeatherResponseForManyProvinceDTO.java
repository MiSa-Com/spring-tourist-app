package com.ms.tourist_app.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ms.tourist_app.domain.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponseForManyProvinceDTO {

    @JsonProperty("coord")
    private Coord coord;

    @JsonProperty("weather")
    private List<Weather> weather;

    @JsonProperty("base")
    private String base;

    @JsonProperty("main")
    private Main main;

    @JsonProperty("visibility")
    private Integer visibility;

    @JsonProperty("wind")
    private Wind wind;

    @JsonProperty("rain")
    private Rain rain;

    @JsonProperty("clouds")
    private Clouds clouds;

    @JsonProperty("dt")
    private int dt;

    @JsonProperty("sys")
    private Sys sys;


    @JsonProperty("timezone")
    private int timezone;

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("cod")
    private int cod;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Clouds {

        @JsonProperty("all")
        private Integer all;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Coord {

        @JsonProperty("lon")
        private Float lon;

        @JsonProperty("lat")
        private Float lat;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Main {

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

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sys {

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

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Weather {

        @JsonProperty("id")
        private Integer id;

        @JsonProperty("main")
        private String main;

        @JsonProperty("description")
        private String description;

        @JsonProperty("icon")
        private String icon;

    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Rain {

        @JsonProperty("0h")
        private Double zeroHours;

        @JsonProperty("1h")
        private Double oneHours;

        @JsonProperty("2h")
        private Double twoHours;

        @JsonProperty("3h")
        private Double threeHours;

        @JsonProperty("4h")
        private Double fourHours;

        @JsonProperty("5h")
        private Double fiveHours;

        @JsonProperty("6h")
        private Double sixHours;

        @JsonProperty("7h")
        private Double sevenHours;

        @JsonProperty("8h")
        private Double eightHours;

        @JsonProperty("9h")
        private Double nineHours;

        @JsonProperty("10h")
        private Double tenHours;

        @JsonProperty("11h")
        private Double elevenHours;

        @JsonProperty("12h")
        private Double twelveHours;

        @JsonProperty("13h")
        private Double thirteenHours;

        @JsonProperty("14h")
        private Double fourteenHours;

        @JsonProperty("15h")
        private Double fifteenHours;

        @JsonProperty("16h")
        private Double sixteenHours;

        @JsonProperty("17h")
        private Double seventeenHours;

        @JsonProperty("18h")
        private Double eighteenHours;

        @JsonProperty("19h")
        private Double nineteenHours;

        @JsonProperty("20h")
        private Double twentyHours;

        @JsonProperty("21h")
        private Double twentyOneHours;

        @JsonProperty("22h")
        private Double twentyTwoHours;

        @JsonProperty("23h")
        private Double twentyThreeHours;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Wind {

        @JsonProperty("speed")
        private Float speed;

        @JsonProperty("deg")
        private Integer deg;

        @JsonProperty("gust")
        private Float gust;

    }
}
