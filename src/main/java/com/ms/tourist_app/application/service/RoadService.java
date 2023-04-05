package com.ms.tourist_app.application.service;

import com.ms.tourist_app.application.input.roads.FindBestRoadFromHotelInput;
import com.ms.tourist_app.application.input.roads.RoadDataInput;
import com.ms.tourist_app.application.output.roads.FindBestRoadFromHotelOutput;
import com.ms.tourist_app.application.output.roads.RoadDataOutput;

public interface RoadService {
    FindBestRoadFromHotelOutput findBestRoadFromHotel(FindBestRoadFromHotelInput findBestRoadFromHotelInput);

    RoadDataOutput createRoad(RoadDataInput roadDataInput);
}
