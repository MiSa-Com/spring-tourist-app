package com.ms.tourist_app.application.service.imp;

import com.google.maps.model.LatLng;
import com.ms.tourist_app.application.dai.ProvinceRepository;
import com.ms.tourist_app.application.input.weathers.GetListWeatherDataInput;
import com.ms.tourist_app.application.input.weathers.GetWeatherDataInput;
import com.ms.tourist_app.application.service.WeatherApiService;
import com.ms.tourist_app.application.utils.WeatherUtils;
import com.ms.tourist_app.domain.dto.WeatherDataDTO;
import com.ms.tourist_app.domain.entity.Province;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherApiServiceImp implements WeatherApiService {


    private final WeatherUtils weatherUtils;
    private final ProvinceRepository provinceRepository;

    public WeatherApiServiceImp(WeatherUtils weatherUtils, ProvinceRepository provinceRepository) {
        this.weatherUtils = weatherUtils;
        this.provinceRepository = provinceRepository;
    }

    @Override
    public List<WeatherDataDTO> getWeatherDataByCoordinates(GetListWeatherDataInput input) throws IOException {
        List<Province> provinces = provinceRepository.findAllByNameContainingIgnoreCase(input.getNameProvince(), PageRequest.of(input.getPage(), input.getSize()));
        List<LatLng> coordinates = new ArrayList<>();
        List<WeatherDataDTO> weatherDataDTOS = new ArrayList<>();
        for (Province province :
                provinces) {
            LatLng latLng = new LatLng(province.getLatitude(), province.getLongitude());
            WeatherDataDTO weatherDataDTO = weatherUtils.getWeatherDataByCoordinates(latLng.lat, latLng.lng);
            weatherDataDTO.setProvince(province.getName());
            weatherDataDTOS.add(weatherDataDTO);
        }
        return weatherDataDTOS;
    }

    @Override
    public WeatherDataDTO getWeatherByCoordinate(GetWeatherDataInput input) throws IOException {
        Optional<Province> province = provinceRepository.findById(input.getIdProvince());
        LatLng latLng = new LatLng(province.get().getLatitude(), province.get().getLongitude());
        WeatherDataDTO weatherDataDTO = weatherUtils.getWeatherDataByCoordinates(latLng.lat, latLng.lng);
        weatherDataDTO.setProvince(province.get().getName());
        return weatherDataDTO;
    }
}
