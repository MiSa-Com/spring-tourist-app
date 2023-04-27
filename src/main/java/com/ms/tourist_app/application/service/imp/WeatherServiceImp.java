package com.ms.tourist_app.application.service.imp;

import com.ms.tourist_app.application.dai.CurrentWeatherRepository;
import com.ms.tourist_app.application.dai.ProvinceRepository;
import com.ms.tourist_app.application.dai.WeatherForecastRepository;
import com.ms.tourist_app.application.input.weathers.GetListWeatherDataInput;
import com.ms.tourist_app.application.input.weathers.GetWeatherDataInput;
import com.ms.tourist_app.application.mapper.WeatherMapper;
import com.ms.tourist_app.application.output.weather.WeatherDataOutputOfAProvince;
import com.ms.tourist_app.application.output.weather.WeatherDataOutputOfListProvince;
import com.ms.tourist_app.application.service.ProvinceService;
import com.ms.tourist_app.application.service.WeatherService;
import com.ms.tourist_app.application.utils.WeatherUtils;
import com.ms.tourist_app.domain.dto.WeatherDataDTO;
import com.ms.tourist_app.domain.entity.CurrentWeather;
import com.ms.tourist_app.domain.entity.Province;
import com.ms.tourist_app.domain.entity.WeatherForcast;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class WeatherServiceImp implements WeatherService {


    private final WeatherUtils weatherUtils;
    private final ProvinceRepository provinceRepository;
    private final WeatherForecastRepository weatherForecastRepository;
    private final CurrentWeatherRepository currentWeatherRepository;
    private final ProvinceService provinceService;
    private final WeatherMapper weatherMapper = Mappers.getMapper(WeatherMapper.class);

    public WeatherServiceImp(WeatherUtils weatherUtils, ProvinceRepository provinceRepository, WeatherForecastRepository weatherForecastRepository, CurrentWeatherRepository currentWeatherRepository, ProvinceService provinceService) {
        this.weatherUtils = weatherUtils;
        this.provinceRepository = provinceRepository;
        this.weatherForecastRepository = weatherForecastRepository;

        this.currentWeatherRepository = currentWeatherRepository;
        this.provinceService = provinceService;
    }

    /**
     * Get data for many province
     **/
    @Override
    public List<WeatherDataOutputOfListProvince> getCurrentWeatherForAllProvince(GetListWeatherDataInput input) {
        List<Province> provinces = provinceRepository.findAllByNameContainingIgnoreCase(input.getNameProvince(),PageRequest.of(input.getPage(), input.getSize()));
        List<WeatherDataOutputOfListProvince> weatherDataOutputOfListProvinces = new ArrayList<>();
        for (Province province: provinces){
            List<CurrentWeather> currentWeathers =  currentWeatherRepository.findAllByProvince(province.getName());
            for(CurrentWeather currentWeather: currentWeathers){
                WeatherDataOutputOfListProvince weatherDataOutputOfListProvince = weatherMapper.fromCurrentWeatherToWeatherDataOutput(currentWeather);
                weatherDataOutputOfListProvinces.add(weatherDataOutputOfListProvince);
            }
        }
        return weatherDataOutputOfListProvinces;
    }



    /**
     * Get data for one province
     **/
    @Override
    public WeatherDataOutputOfAProvince getWeatherForecastForAProvince(GetWeatherDataInput input)  {
        Optional<Province> province = provinceRepository.findById(input.getIdProvince());
        List<WeatherForcast> weatherForcasts = weatherForecastRepository.findAllByIdProvince(input.getIdProvince());
        List<WeatherDataOutputOfAProvince.WeatherInformation> weatherInformations = new ArrayList<>();
        for(WeatherForcast weatherForcast: weatherForcasts){
            WeatherDataOutputOfAProvince.WeatherInformation weatherInformation = new WeatherDataOutputOfAProvince.WeatherInformation(
                    weatherForcast.getDateTime(),
                    weatherForcast.getTemp(),
                    weatherForcast.getFeelsLike(),
                    weatherForcast.getTempMin(),
                    weatherForcast.getTempMax(),
                    weatherForcast.getHumidity(),
                    weatherForcast.getMain(),
                    weatherForcast.getDescription());
            weatherInformations.add(weatherInformation);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<WeatherDataOutputOfAProvince.WeatherInformation> sortedList = weatherInformations.stream()
                .sorted(Comparator.comparing(o -> LocalDateTime.parse(o.getDateTime(), formatter)))
                .collect(Collectors.toList());
        CurrentWeather currentWeather = currentWeatherRepository.findByIdProvince(input.getIdProvince());
        WeatherDataOutputOfAProvince.CurrentWeather currentWeatherResult = new WeatherDataOutputOfAProvince.CurrentWeather(
                currentWeather.getTemp(),
                currentWeather.getFeelsLike(),
                currentWeather.getTempMin(),
                currentWeather.getTempMax(),
                currentWeather.getHumidity(),
                currentWeather.getMain(),
                currentWeather.getDescription());
        return new WeatherDataOutputOfAProvince(input.getIdProvince(), province.get().getName(),currentWeatherResult,sortedList);
    }

    @Override
    public WeatherDataOutputOfAProvince getWeatherForecastByCoordinate(Double lon, Double lat) {
        Long idProvince = provinceService.getProvinceByCoordinate(lon,lat);
        Optional<Province> province = provinceRepository.findById(idProvince);
        WeatherDataOutputOfAProvince weatherDataOutputOfAProvince = new WeatherDataOutputOfAProvince(idProvince,province.get().getName());
        List<WeatherForcast> weatherForcasts = weatherForecastRepository.findAllByIdProvince(idProvince);
        List<WeatherDataOutputOfAProvince.WeatherInformation> weatherInformations = new ArrayList<>();
        for(WeatherForcast weatherForcast: weatherForcasts){
            WeatherDataOutputOfAProvince.WeatherInformation weatherInformation = new WeatherDataOutputOfAProvince.WeatherInformation(
                    weatherForcast.getDateTime(),
                    weatherForcast.getTemp(),
                    weatherForcast.getFeelsLike(),
                    weatherForcast.getTempMin(),
                    weatherForcast.getTempMax(),
                    weatherForcast.getHumidity(),
                    weatherForcast.getMain(),
                    weatherForcast.getDescription());
            weatherInformations.add(weatherInformation);
        }        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<WeatherDataOutputOfAProvince.WeatherInformation> sortedList = weatherInformations.stream()
                .sorted(Comparator.comparing(o -> LocalDateTime.parse(o.getDateTime(), formatter)))
                .collect(Collectors.toList());
        weatherDataOutputOfAProvince.setWeatherInformationList(sortedList);
        CurrentWeather currentWeather = currentWeatherRepository.findByIdProvince(idProvince);
        WeatherDataOutputOfAProvince.CurrentWeather currentWeatherResult = new WeatherDataOutputOfAProvince.CurrentWeather(
                currentWeather.getTemp(),
                currentWeather.getFeelsLike(),
                currentWeather.getTempMin(),
                currentWeather.getTempMax(),
                currentWeather.getHumidity(),
                currentWeather.getMain(),
                currentWeather.getDescription());
        weatherDataOutputOfAProvince.setCurrentWeather(currentWeatherResult);
        return weatherDataOutputOfAProvince;
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
