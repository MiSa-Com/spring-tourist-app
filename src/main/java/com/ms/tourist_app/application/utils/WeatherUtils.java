package com.ms.tourist_app.application.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.tourist_app.application.constants.AppConst;
import com.ms.tourist_app.application.dai.ProvinceRepository;
import com.ms.tourist_app.domain.dto.WeatherDataDTO;
import com.ms.tourist_app.domain.dto.WeatherResponseForManyProvinceDTO;
import com.ms.tourist_app.domain.dto.WeatherResponseForOneProvinceDTO;
import com.ms.tourist_app.domain.entity.Province;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class WeatherUtils {
    private final ProvinceRepository provinceRepository;

    public WeatherUtils(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    /**
     * Laays data 5 ngày của 1 địa điểm
     * Get data for one province**/
    public List<WeatherDataDTO> getWeatherForecastDataForAProvince(Long idProvince) throws IOException {
        Optional<Province> province = provinceRepository.findById(idProvince);
        HttpClient httpClient = HttpClients.createDefault();
        String API_KEY = "ec2e3acfd4f07b705ff0a828ec6c228e";
        String api_url = "https://api.openweathermap.org/data/2.5/forecast?lat=" + province.get().getLatitude().toString() + "&lon=" + province.get().getLongitude().toString() + "&appid=" + API_KEY;
        HttpGet httpGet = new HttpGet(api_url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        String json = EntityUtils.toString(httpResponse.getEntity());
        ObjectMapper mapper = new ObjectMapper();
        WeatherResponseForOneProvinceDTO weatherResponseForOneProvinceDTO = mapper.readValue(json, WeatherResponseForOneProvinceDTO.class);
        List<WeatherDataDTO> weatherDataDTOS = new ArrayList<>();
        List<WeatherResponseForOneProvinceDTO.WeatherInfo> weatherInfos = weatherResponseForOneProvinceDTO.getList();
        for (WeatherResponseForOneProvinceDTO.WeatherInfo weatherInfo
                : weatherInfos
             ) {
            WeatherDataDTO weatherDataDTO = new WeatherDataDTO();
            weatherDataDTO.setIdProvince(idProvince);
            weatherDataDTO.setTemp((float) (weatherInfo.getMain().getTemp()- AppConst.Temp.kToC));
            weatherDataDTO.setFeelsLike((float) (weatherInfo.getMain().getFeels_like()- AppConst.Temp.kToC));
            weatherDataDTO.setTempMin((float) (weatherInfo.getMain().getTemp_min()- AppConst.Temp.kToC));
            weatherDataDTO.setTempMax((float) (weatherInfo.getMain().getTemp_max()- AppConst.Temp.kToC));
            weatherDataDTO.setHumidity(weatherInfo.getMain().getHumidity());
            weatherDataDTO.setMain(weatherInfo.getWeather().get(0).getMain());
            weatherDataDTO.setDescription(weatherInfo.getWeather().get(0).getDescription());
            weatherDataDTO.setProvince(province.get().getName());
            weatherDataDTO.setDateTime(weatherInfo.getDt_txt());
            weatherDataDTOS.add(weatherDataDTO);
        }

        return weatherDataDTOS;
    }

    /**
     * Lấy data hiện tại của 1 địa điểm
     * Get data for one province**/
    public WeatherDataDTO getCurrentWeatherForAProvince(Long idProvince) throws IOException{
        Optional<Province> province = provinceRepository.findById(idProvince);
        HttpClient httpClient = HttpClients.createDefault();
        String API_KEY = "ec2e3acfd4f07b705ff0a828ec6c228e";
        String api_url = "https://api.openweathermap.org/data/2.5/weather?lat=" + province.get().getLatitude().toString() + "&lon=" + province.get().getLongitude().toString() + "&appid=" + API_KEY;
        HttpGet httpGet = new HttpGet(api_url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        String json = EntityUtils.toString(httpResponse.getEntity());
        ObjectMapper mapper = new ObjectMapper();
        WeatherResponseForManyProvinceDTO weatherResponseForManyProvinceDTO = mapper.readValue(json, WeatherResponseForManyProvinceDTO.class);
        WeatherDataDTO weatherDataDTO = new WeatherDataDTO();
        weatherDataDTO.setIdProvince(idProvince);
        weatherDataDTO.setTemp((float) (weatherResponseForManyProvinceDTO.getMain().getTemp()- AppConst.Temp.kToC));
        weatherDataDTO.setFeelsLike((float) (weatherResponseForManyProvinceDTO.getMain().getFeelsLike()- AppConst.Temp.kToC));
        weatherDataDTO.setTempMin((float) (weatherResponseForManyProvinceDTO.getMain().getTempMin()- AppConst.Temp.kToC));
        weatherDataDTO.setTempMax((float) (weatherResponseForManyProvinceDTO.getMain().getTempMax()- AppConst.Temp.kToC));
        weatherDataDTO.setHumidity(weatherResponseForManyProvinceDTO.getMain().getHumidity());
        weatherDataDTO.setMain(weatherResponseForManyProvinceDTO.getWeather().get(0).getMain());
        weatherDataDTO.setDescription(weatherResponseForManyProvinceDTO.getWeather().get(0).getDescription());
        return weatherDataDTO;
    }
}
