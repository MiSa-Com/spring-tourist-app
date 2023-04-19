package com.ms.tourist_app.adapter.web.v1.controller;

import com.ms.tourist_app.adapter.web.base.ResponseUtil;
import com.ms.tourist_app.adapter.web.base.RestApiV1;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.weathers.GetListWeatherDataParameter;
import com.ms.tourist_app.application.constants.UrlConst;
import com.ms.tourist_app.application.input.weathers.GetListWeatherDataInput;
import com.ms.tourist_app.application.input.weathers.GetWeatherDataInput;
import com.ms.tourist_app.application.service.imp.WeatherApiServiceImp;
import com.ms.tourist_app.domain.dto.WeatherDataDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestApiV1
public class WeatherController {
    private final WeatherApiServiceImp weatherApiServiceImp;

    public WeatherController(WeatherApiServiceImp weatherApiServiceImp) {
        this.weatherApiServiceImp = weatherApiServiceImp;
    }

    @GetMapping(UrlConst.Weather.getWeatherById)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> getWeatherById(@PathVariable(UrlConst.id)Long id) throws IOException {
        GetWeatherDataInput input = new GetWeatherDataInput(id);
        List<WeatherDataDTO> output = weatherApiServiceImp.getWeatherByCoordinate(input);
        return ResponseUtil.restSuccess(output);
    }

    @GetMapping(UrlConst.Weather.weather)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> getListWeather(@Valid GetListWeatherDataParameter parameter) throws IOException {
        GetListWeatherDataInput input = new GetListWeatherDataInput(parameter.getKeyword(),parameter.getPage(),parameter.getSize());
        List<WeatherDataDTO> output = weatherApiServiceImp.getAllWeatherData(input);
        return ResponseUtil.restSuccess(output);
    }
}
