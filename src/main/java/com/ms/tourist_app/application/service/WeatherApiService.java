package com.ms.tourist_app.application.service;

import com.cloudinary.Coordinates;
import com.google.maps.model.LatLng;
import com.ms.tourist_app.application.input.weathers.GetListWeatherDataInput;
import com.ms.tourist_app.application.input.weathers.GetWeatherDataInput;
import com.ms.tourist_app.domain.dto.WeatherDataDTO;

import java.io.IOException;
import java.util.List;

public interface WeatherApiService {
    List<WeatherDataDTO> getWeatherDataByCoordinates(GetListWeatherDataInput input) throws IOException;
    WeatherDataDTO getWeatherByCoordinate(GetWeatherDataInput input) throws IOException;
}
