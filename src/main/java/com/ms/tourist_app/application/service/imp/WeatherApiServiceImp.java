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

    /**
     * Get data for many province**/
    @Override
    public List<WeatherDataDTO> getAllWeatherData(GetListWeatherDataInput input) throws IOException {
        List<Province> provinces = provinceRepository.findAllByNameContainingIgnoreCase(input.getNameProvince(), PageRequest.of(input.getPage(), input.getSize()));
        List<WeatherDataDTO> weatherDataDTOS = new ArrayList<>();
        for (Province province :
                provinces) {
            LatLng latLng = new LatLng(province.getLatitude(), province.getLongitude());
            WeatherDataDTO weatherDataDTO = weatherUtils.getWeatherForManyProvince(province.getId(),latLng.lat, latLng.lng,1);
            weatherDataDTO.setProvince(province.getName());
            weatherDataDTOS.add(weatherDataDTO);
        }
        return weatherDataDTOS;
    }


    /**
     * Get data for one province**/
    @Override
    public List<WeatherDataDTO> getWeatherByCoordinate(GetWeatherDataInput input) throws IOException {
        Optional<Province> province = provinceRepository.findById(input.getIdProvince());
        List<WeatherDataDTO> weatherDataDTOS = new ArrayList<>();
        if(province.isPresent()){
            LatLng latLng = new LatLng(province.get().getLatitude(), province.get().getLongitude());
            weatherDataDTOS = weatherUtils.getWeatherDataByCoordinates(province.get().getId(),latLng.lat, latLng.lng,40);
        }
        return weatherDataDTOS;
    }
}
