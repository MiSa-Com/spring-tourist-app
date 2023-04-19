package com.ms.tourist_app.application.service;

import com.ms.tourist_app.application.input.weathers.GetListWeatherDataInput;
import com.ms.tourist_app.application.input.weathers.GetWeatherDataInput;
import com.ms.tourist_app.domain.dto.WeatherDataDTO;

import java.io.IOException;
import java.util.List;

public interface WeatherApiService {
    List<WeatherDataDTO> getAllWeatherData(GetListWeatherDataInput input) throws IOException;
    List<WeatherDataDTO> getWeatherByCoordinate(GetWeatherDataInput input) throws IOException;
}
