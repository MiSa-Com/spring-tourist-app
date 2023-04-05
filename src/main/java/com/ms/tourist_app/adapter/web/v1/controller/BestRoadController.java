package com.ms.tourist_app.adapter.web.v1.controller;

import com.ms.tourist_app.adapter.web.base.ResponseUtil;
import com.ms.tourist_app.adapter.web.base.RestApiV1;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.roads.FindBestRoadFromHotelParameter;
import com.ms.tourist_app.application.constants.UrlConst;
import com.ms.tourist_app.application.input.roads.FindBestRoadFromHotelInput;
import com.ms.tourist_app.application.output.roads.FindBestRoadFromHotelOutput;
import com.ms.tourist_app.application.service.imp.RoadServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RestApiV1
public class BestRoadController {
    private final RoadServiceImp roadService;

    public BestRoadController(RoadServiceImp roadService) {
        this.roadService = roadService;
    }
    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping(UrlConst.Road.bestroad)
    public ResponseEntity<?> findBestRoadFromHotel(@Valid @RequestBody FindBestRoadFromHotelParameter parameter) {
        FindBestRoadFromHotelInput findBestRoadFromHotelInput = new FindBestRoadFromHotelInput();
        String listIds = parameter.getIdHotel() + ",";
        for (Long idDest : parameter.getListIdDestination()) {
            listIds += (idDest + ",");
        }
        findBestRoadFromHotelInput.setRoad(listIds);
        FindBestRoadFromHotelOutput findBestRoadFromHotelOutput = roadService.findBestRoadFromHotel(findBestRoadFromHotelInput);
        return ResponseUtil.restSuccess(findBestRoadFromHotelOutput);
    }
}
