package com.ms.tourist_app.application.service;

import com.ms.tourist_app.application.input.roads.RoadDataInput;
import com.ms.tourist_app.application.output.roads.RoadDataOutput;

public interface RoadService {
    RoadDataOutput createRoad(RoadDataInput roadDataInput);
}
