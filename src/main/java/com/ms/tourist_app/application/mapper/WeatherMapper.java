package com.ms.tourist_app.application.mapper;

import com.ms.tourist_app.application.output.weather.WeatherDataOutputOfListProvince;
import com.ms.tourist_app.domain.dto.WeatherDataDTO;
import com.ms.tourist_app.domain.entity.CurrentWeather;
import com.ms.tourist_app.domain.entity.WeatherForcast;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface WeatherMapper {


    WeatherForcast toWeatherForecast(WeatherDataDTO weatherDataDTO);

    @Mappings({
            @Mapping(target = "idProvince",source = "idProvince"),
            @Mapping(target = "province",source = "province"),
            @Mapping(target = "dateTime",source = "dateTime"),
            @Mapping(target = "temp",source = "temp"),
            @Mapping(target = "feelsLike",source = "feelsLike"),
            @Mapping(target = "tempMin",source = "tempMin"),
            @Mapping(target = "tempMax",source = "tempMax"),
            @Mapping(target = "humidity",source = "humidity"),
            @Mapping(target = "main",source = "main"),
            @Mapping(target = "description",source = "description")
    })
    WeatherDataOutputOfListProvince fromWeatherForcastToWeatherDataOutput(WeatherForcast weatherForcast);
    @Mappings({
            @Mapping(target = "idProvince",source = "idProvince"),
            @Mapping(target = "province",source = "province"),
            @Mapping(target = "dateTime",source = "dateTime"),
            @Mapping(target = "temp",source = "temp"),
            @Mapping(target = "feelsLike",source = "feelsLike"),
            @Mapping(target = "tempMin",source = "tempMin"),
            @Mapping(target = "tempMax",source = "tempMax"),
            @Mapping(target = "humidity",source = "humidity"),
            @Mapping(target = "main",source = "main"),
            @Mapping(target = "description",source = "description")
    })
    WeatherDataOutputOfListProvince fromCurrentWeatherToWeatherDataOutput(CurrentWeather currentWeather);


    CurrentWeather toCurrentWeather(WeatherDataDTO weatherDataDTO);
}
