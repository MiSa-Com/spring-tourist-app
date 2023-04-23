package com.ms.tourist_app.application.service;

import com.ms.tourist_app.application.input.weathers.GetListWeatherDataInput;
import com.ms.tourist_app.application.input.weathers.GetWeatherDataInput;
import com.ms.tourist_app.application.output.weather.WeatherDataOutputOfAProvince;
import com.ms.tourist_app.application.output.weather.WeatherDataOutputOfListProvince;

import java.io.IOException;
import java.util.List;

public interface WeatherService {
    List<WeatherDataOutputOfListProvince> getCurrentWeatherForAllProvince(GetListWeatherDataInput input);
    WeatherDataOutputOfAProvince getWeatherForecastForAProvince(GetWeatherDataInput input) ;
    WeatherDataOutputOfAProvince getWeatherForecastByCoordinate(Double lon, Double lat);
    void chargeWeatherForeCastIntoDatabase() throws IOException;
    void chargeCurrentWeatherIntoDatabase() throws IOException;

}
