package com.ms.tourist_app.adapter.web.v1.controller;

import com.ms.tourist_app.adapter.web.base.ResponseUtil;
import com.ms.tourist_app.adapter.web.base.RestApiV1;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.hotels.HotelDataParameter;
import com.ms.tourist_app.application.constants.UrlConst;
import com.ms.tourist_app.application.input.hotels.HotelDataInput;
import com.ms.tourist_app.application.mapper.HotelMapper;
import com.ms.tourist_app.application.output.hotels.HotelDataOutput;
import com.ms.tourist_app.application.service.HotelService;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RestApiV1
public class HotelController {

    private final HotelMapper hotelMapper = Mappers.getMapper(HotelMapper.class);
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping(UrlConst.Hotel.hotel)
    public ResponseEntity<?> createHotel(@Valid HotelDataParameter parameter){
        HotelDataInput hotelDataInput = hotelMapper.createHotelDataInput(parameter);
        HotelDataOutput hotelDataOutput = hotelService.createHotel(hotelDataInput);
        return ResponseUtil.restSuccess(hotelDataOutput);
    }
}
