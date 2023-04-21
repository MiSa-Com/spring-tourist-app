package com.ms.tourist_app.application.service.imp;

import com.google.maps.model.LatLng;
import com.ms.tourist_app.application.dai.CurrentWeatherRepository;
import com.ms.tourist_app.application.dai.ProvinceRepository;
import com.ms.tourist_app.application.dai.WeatherForecastRepository;
import com.ms.tourist_app.application.input.weathers.GetListWeatherDataInput;
import com.ms.tourist_app.application.input.weathers.GetWeatherDataInput;
import com.ms.tourist_app.application.mapper.WeatherMapper;
import com.ms.tourist_app.application.output.weather.WeatherDataOutput;
import com.ms.tourist_app.application.service.WeatherApiService;
import com.ms.tourist_app.application.utils.WeatherUtils;
import com.ms.tourist_app.domain.dto.WeatherDataDTO;
import com.ms.tourist_app.domain.entity.CurrentWeather;
import com.ms.tourist_app.domain.entity.Province;
import com.ms.tourist_app.domain.entity.WeatherForcast;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
public class WeatherApiServiceImp implements WeatherApiService {


    private final WeatherUtils weatherUtils;
    private final ProvinceRepository provinceRepository;
    private final WeatherForecastRepository weatherForecastRepository;
    private final CurrentWeatherRepository currentWeatherRepository;
    private final WeatherMapper weatherMapper = Mappers.getMapper(WeatherMapper.class);

    public WeatherApiServiceImp(WeatherUtils weatherUtils, ProvinceRepository provinceRepository, WeatherForecastRepository weatherForecastRepository, CurrentWeatherRepository currentWeatherRepository, WeatherMapper weatherMapper) {
        this.weatherUtils = weatherUtils;
        this.provinceRepository = provinceRepository;
        this.weatherForecastRepository = weatherForecastRepository;

        this.currentWeatherRepository = currentWeatherRepository;
    }

    /**
     * Get data for many province
     **/
    @Override
    public List<WeatherDataOutput> getCurrentWeatherForAllProvince(GetListWeatherDataInput input) {
        List<Province> provinces = provinceRepository.findAllByNameContainingIgnoreCase(input.getNameProvince(),PageRequest.of(input.getPage(), input.getSize()));
        List<WeatherDataOutput> weatherDataOutputs = new ArrayList<>();
        for (Province province: provinces){
            List<CurrentWeather> currentWeathers =  currentWeatherRepository.findAllByProvince(province.getName());
            for(CurrentWeather currentWeather: currentWeathers){
                WeatherDataOutput weatherDataOutput = weatherMapper.fromCurrentWeatherToWeatherDataOutput(currentWeather);
                weatherDataOutputs.add(weatherDataOutput);
            }
        }
        return weatherDataOutputs;
    }


    /**
     * Get data for one province
     **/
    @Override
    public List<WeatherDataOutput> getWeatherForecastForAProvince(GetWeatherDataInput input)  {
        List<WeatherDataOutput> weatherDataOutputs = new ArrayList<>();
        List<WeatherForcast> weatherForcasts = weatherForecastRepository.findAllByIdProvince(input.getIdProvince());
        for(WeatherForcast weatherForcast: weatherForcasts){
            WeatherDataOutput weatherDataOutput = weatherMapper.fromWeatherForcastToWeatherDataOutput(weatherForcast);
            weatherDataOutputs.add(weatherDataOutput);
        }
        return weatherDataOutputs;
    }

    @Override
    public void chargeWeatherForeCastIntoDatabase() throws IOException {
        weatherForecastRepository.deleteAll();
        List<Province> provinces = provinceRepository.findAll();
        for (Province province : provinces) {
            List<WeatherDataDTO> weatherDataDTOS = weatherUtils.getWeatherForecastDataForAProvince(province.getId());
            for (WeatherDataDTO weatherDataDTO : weatherDataDTOS) {
                WeatherForcast weatherForcast = weatherMapper.toWeatherForecast(weatherDataDTO);
                weatherForecastRepository.save(weatherForcast);
            }
        }
    }

    @Override
    public void chargeCurrentWeatherIntoDatabase() throws IOException {
        currentWeatherRepository.deleteAll();
        List<Province> provinces = provinceRepository.findAll();
        for (Province province : provinces) {
            WeatherDataDTO weatherDataDTO = weatherUtils.getCurrentWeatherForAProvince(province.getId());
            weatherDataDTO.setProvince(province.getName());
            CurrentWeather currentWeather = weatherMapper.toCurrentWeather(weatherDataDTO);
            currentWeatherRepository.save(currentWeather);
        }
    }


}
