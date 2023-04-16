package com.ms.tourist_app.adapter.web.v1.controller;

import com.ms.tourist_app.adapter.web.base.ResponseUtil;
import com.ms.tourist_app.adapter.web.base.RestApiV1;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.destinations.DestinationDataParameter;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.destinations.GetListDestinationByKeywordParameter;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.destinations.GetListDestinationByProvinceParameter;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.destinations.GetListDestinationCenterRadiusParameter;
import com.ms.tourist_app.application.constants.UrlConst;
import com.ms.tourist_app.application.input.destinations.DestinationDataInput;
import com.ms.tourist_app.application.input.destinations.GetListDestinationByKeywordInput;
import com.ms.tourist_app.application.input.destinations.GetListDestinationCenterRadiusInput;
import com.ms.tourist_app.application.input.destinations.GetListDestinationByProvinceInput;
import com.ms.tourist_app.application.mapper.DestinationMapper;
import com.ms.tourist_app.application.output.destinations.DestinationDataOutput;
import com.ms.tourist_app.application.service.DestinationService;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping(UrlConst.Destination.destination)
    public ResponseEntity<?> createDestination(@Valid DestinationDataParameter parameter){
        DestinationDataInput dataInput = destinationMapper.createDestinationInput(parameter);
        DestinationDataOutput dataOutput = destinationService.createDestination(dataInput);
        return ResponseUtil.restSuccess(dataOutput);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(UrlConst.Destination.destinationFilter)
    public ResponseEntity<?>  getListDestinationByProvince(@Valid GetListDestinationByProvinceParameter parameter){
        GetListDestinationByProvinceInput input = new GetListDestinationByProvinceInput(parameter.getPage(), parameter.getSize(),parameter.getIdProvince());
        List<DestinationDataOutput> outputs = destinationService.getListDestinationByProvince(input);
        return ResponseUtil.restSuccess(outputs);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(UrlConst.Destination.destination)
    public ResponseEntity<?>  getListDestinationFilter(@Valid GetListDestinationByKeywordParameter parameter){
        GetListDestinationByKeywordInput input = new GetListDestinationByKeywordInput(parameter.getKeyword(),parameter.getPage(), parameter.getSize());
        List<DestinationDataOutput> outputs = destinationService.getListDestinationByKeyword(input);
        return ResponseUtil.restSuccess(outputs);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(UrlConst.Destination.destinationRadius)
    public ResponseEntity<?> getListDestinationCenterRadius(@Valid GetListDestinationCenterRadiusParameter parameter) {
        GetListDestinationCenterRadiusInput input = new GetListDestinationCenterRadiusInput(parameter.getPage(),parameter.getSize(), parameter.getKeyword(), parameter.getRadius());
        List<DestinationDataOutput> outputs = destinationService.getListDestinationCenterRadius(input);
        return ResponseUtil.restSuccess(outputs);
    }
}
