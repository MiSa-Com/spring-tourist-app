package com.ms.tourist_app.application.service;

import com.ms.tourist_app.application.input.weathers.GetListWeatherDataInput;
import com.ms.tourist_app.application.input.weathers.GetWeatherDataInput;
import com.ms.tourist_app.application.output.weather.WeatherDataOutput;

import java.io.IOException;
import java.util.List;

public interface WeatherService {
    List<WeatherDataOutput> getCurrentWeatherForAllProvince(GetListWeatherDataInput input);
    List<WeatherDataOutput> getWeatherForecastForAProvince(GetWeatherDataInput input) ;
    List<WeatherDataOutput> getWeatherForecastByCoordinate(Double lon,Double lat);
    void chargeWeatherForeCastIntoDatabase() throws IOException;
    void chargeCurrentWeatherIntoDatabase() throws IOException;

}
