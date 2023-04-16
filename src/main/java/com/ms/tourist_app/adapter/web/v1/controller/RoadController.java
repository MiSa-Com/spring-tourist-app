package com.ms.tourist_app.adapter.web.v1.controller;

import com.ms.tourist_app.adapter.web.base.ResponseUtil;
import com.ms.tourist_app.adapter.web.base.RestApiV1;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.roads.FindBestRoadFromHotelParameter;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.roads.RoadDataParameter;
import com.ms.tourist_app.application.constants.UrlConst;
import com.ms.tourist_app.application.input.roads.RoadDataInput;
import com.ms.tourist_app.application.output.roads.RoadDataOutput;
import com.ms.tourist_app.application.service.imp.RoadServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RestApiV1
public class RoadController {
    private final RoadServiceImp roadService;

    public RoadController(RoadServiceImp roadService) {
        this.roadService = roadService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping(UrlConst.Road.road)
    public ResponseEntity<?> createRoad(@Valid @RequestBody RoadDataParameter parameter){
        String road = "Hotel " + parameter.getIdHotel() + " - " + parameter.getListTime().get(0) + " | ";
        for (int i = 0 ; i < parameter.getListIdDestination().size() ; i++) {
            road += (parameter.getListIdDestination().get(i) + " - " +
                    parameter.getListTime().get(i+1) + " | ");
        }
        RoadDataInput roadDataInput = new RoadDataInput(road, parameter.getIdUser());
        RoadDataOutput roadDataOutput = roadService.createRoad(roadDataInput);
        return ResponseUtil.restSuccess(roadDataOutput);
    }
}
