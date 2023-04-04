package com.ms.tourist_app.application.service;

import com.ms.tourist_app.application.input.hotels.GetListHotelDataInput;
import com.ms.tourist_app.application.input.hotels.HotelDataInput;
import com.ms.tourist_app.application.output.hotels.HotelDataOutput;

import java.util.List;

public interface HotelService {
    HotelDataOutput createHotel(HotelDataInput hotelDataInput);
    HotelDataOutput editHotel(Long id, HotelDataInput hotelDataInput);
    List<HotelDataOutput> getListHotel(GetListHotelDataInput getListHotelDataInput);
    HotelDataOutput viewHotelDetail(Long id);
    HotelDataOutput deleteHotel(Long id);
}
