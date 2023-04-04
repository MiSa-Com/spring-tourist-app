package com.ms.tourist_app.adapter.web.v1.controller;

import com.ms.tourist_app.adapter.web.base.ResponseUtil;
import com.ms.tourist_app.adapter.web.base.RestApiV1;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.roads.RoadDataParameter;
import com.ms.tourist_app.application.constants.UrlConst;
import com.ms.tourist_app.application.input.roads.RoadDataInput;
import com.ms.tourist_app.application.output.roads.RoadDataOutput;
import com.ms.tourist_app.application.service.imp.RoadServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RestApiV1
public class RoadController {
    private final RoadServiceImp roadService;

    public RoadController(RoadServiceImp roadService) {
        this.roadService = roadService;
    }


    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping(UrlConst.Road.road)
    public ResponseEntity<?> createRoad(@Valid @RequestBody RoadDataParameter parameter){
        RoadDataInput roadDataInput = new RoadDataInput();
        roadDataInput.setIdUser(parameter.getIdUser());
        String listIds = "";
        for (Long idDest : parameter.getListIdDestination()) {
            listIds += (idDest + ",");
        }
        roadDataInput.setRoad(listIds);
        RoadDataOutput roadDataOutput = roadService.createRoad(roadDataInput);
        return ResponseUtil.restSuccess(roadDataOutput);
    }
}
