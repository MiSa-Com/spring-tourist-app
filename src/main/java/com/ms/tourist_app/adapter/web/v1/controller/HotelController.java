package com.ms.tourist_app.adapter.web.v1.controller;

import com.ms.tourist_app.adapter.web.base.ResponseUtil;
import com.ms.tourist_app.adapter.web.base.RestApiV1;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.hotels.GetListHotelDataParameter;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.hotels.HotelDataParameter;
import com.ms.tourist_app.application.constants.UrlConst;
import com.ms.tourist_app.application.input.hotels.GetListHotelDataInput;
import com.ms.tourist_app.application.input.hotels.HotelDataInput;
import com.ms.tourist_app.application.mapper.HotelMapper;
import com.ms.tourist_app.application.output.hotels.HotelDataOutput;
import com.ms.tourist_app.application.service.HotelService;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestApiV1
public class HotelController {

    private final HotelMapper hotelMapper = Mappers.getMapper(HotelMapper.class);
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping(UrlConst.Hotel.hotel)
    public ResponseEntity<?> createHotel(@Valid HotelDataParameter parameter){
        HotelDataInput hotelDataInput = hotelMapper.createHotelDataInput(parameter);
        HotelDataOutput hotelDataOutput = hotelService.createHotel(hotelDataInput);
        return ResponseUtil.restSuccess(hotelDataOutput);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(UrlConst.Hotel.hotel)
    public ResponseEntity<?> getListHotel(@Valid GetListHotelDataParameter parameter){
        GetListHotelDataInput hotelDataInput = new GetListHotelDataInput(parameter.getKeyword(), parameter.getPage(), parameter.getIdProvince(), parameter.getSize());
        List<HotelDataOutput> hotelDataOutputs = hotelService.getListHotel(hotelDataInput);
        return ResponseUtil.restSuccess(hotelDataOutputs);

    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(UrlConst.Hotel.getHotelById)
    public ResponseEntity<?> getDataHotel(@PathVariable("id")Long id){
        HotelDataOutput hotelDataOutput = hotelService.viewHotelDetail(id);
        return ResponseUtil.restSuccess(hotelDataOutput);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping(UrlConst.Hotel.getHotelById)
    public ResponseEntity<?> editHotel(@PathVariable("id")Long id, @Valid HotelDataParameter hotelDataParameter){
        HotelDataInput hotelDataInput = hotelMapper.createHotelDataInput(hotelDataParameter);
        HotelDataOutput hotelDataOutput = hotelService.editHotel(id,hotelDataInput);
        return ResponseUtil.restSuccess(hotelDataOutput);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping(UrlConst.Hotel.getHotelById)
    public ResponseEntity<?> deleteHotel(@PathVariable("id")Long id){
        HotelDataOutput hotelDataOutput = hotelService.deleteHotel(id);
        return ResponseUtil.restSuccess(hotelDataOutput);
    }
}
