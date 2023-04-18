package com.ms.tourist_app.application.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.tourist_app.application.constants.AppConst;
import com.ms.tourist_app.domain.dto.WeatherDataDTO;
import com.ms.tourist_app.domain.dto.WeatherResponseDTO;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@Service
public class WeatherUtils {

    public WeatherDataDTO getWeatherDataByCoordinates(Double lat, Double lon) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        String API_KEY = "ec2e3acfd4f07b705ff0a828ec6c228e";
        String api_url = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat.toString() + "&lon=" + lon.toString() + "&appid=" + API_KEY + "&cnt=5";
        HttpGet httpGet = new HttpGet(api_url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        String json = EntityUtils.toString(httpResponse.getEntity());
        ObjectMapper mapper = new ObjectMapper();
        WeatherResponseDTO weatherResponseDTO = mapper.readValue(json, WeatherResponseDTO.class);
        WeatherDataDTO weatherDataDTO = new WeatherDataDTO();
        weatherDataDTO.setTemp((float) (weatherResponseDTO.getMain().getTemp()- AppConst.Temp.kToC));
        weatherDataDTO.setFeelsLike((float) (weatherResponseDTO.getMain().getFeelsLike()- AppConst.Temp.kToC));
        weatherDataDTO.setTempMin((float) (weatherResponseDTO.getMain().getTempMin()- AppConst.Temp.kToC));
        weatherDataDTO.setTempMax((float) (weatherResponseDTO.getMain().getTempMax()- AppConst.Temp.kToC));
        weatherDataDTO.setHumidity(weatherResponseDTO.getMain().getHumidity());
        weatherDataDTO.setMain(weatherResponseDTO.getWeather().get(0).getMain());
        weatherDataDTO.setDescription(weatherResponseDTO.getWeather().get(0).getDescription());
        return weatherDataDTO;
    }
}
