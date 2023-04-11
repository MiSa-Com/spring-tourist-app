package com.ms.tourist_app.adapter.web.v1.controller;

import com.ms.tourist_app.adapter.web.base.ResponseUtil;
import com.ms.tourist_app.adapter.web.base.RestApiV1;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.destinations.DestinationDataParameter;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.destinations.GetListDestinationCenterRadiusParameter;
import com.ms.tourist_app.application.constants.UrlConst;
import com.ms.tourist_app.application.input.destinations.DestinationDataInput;
import com.ms.tourist_app.application.input.destinations.GetListDestinationCenterRadiusInput;
import com.ms.tourist_app.application.mapper.DestinationMapper;
import com.ms.tourist_app.application.output.destinations.DestinationDataOutput;
import com.ms.tourist_app.application.service.DestinationService;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@RestApiV1
public class DestinationController {
    private final DestinationMapper destinationMapper = Mappers.getMapper(DestinationMapper.class);
    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @PostMapping(UrlConst.Destination.destination)
    public ResponseEntity<?> createDestination(@Valid DestinationDataParameter parameter){
        DestinationDataInput dataInput = destinationMapper.createDestinationInput(parameter);
        DestinationDataOutput dataOutput = destinationService.createDestination(dataInput);
        return ResponseUtil.restSuccess(dataOutput);
    }

    @GetMapping(UrlConst.Destination.destination)
    public ResponseEntity<?> getListDestinationCenterRadius(@Valid GetListDestinationCenterRadiusParameter parameter) {
        GetListDestinationCenterRadiusInput input = new GetListDestinationCenterRadiusInput(parameter.getPage(),parameter.getSize(), parameter.getKeyword(),
                                            parameter.getRadius(), parameter.getMaxResult());
        List<DestinationDataOutput> outputs = destinationService.getListDestinationCenterRadius(input);
        return ResponseUtil.restSuccess(outputs);
    }
}
