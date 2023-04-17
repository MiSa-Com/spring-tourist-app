package com.ms.tourist_app.adapter.web.v1.controller;

import com.ms.tourist_app.adapter.web.base.ResponseUtil;
import com.ms.tourist_app.adapter.web.base.RestApiV1;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.destination_type.DestinationTypeDataParameter;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.destination_type.GetListDestinationTypeParameter;
import com.ms.tourist_app.application.constants.UrlConst;
import com.ms.tourist_app.application.input.type_destination.DestinationTypeDataInput;
import com.ms.tourist_app.application.input.type_destination.GetListDestinationTypeInput;
import com.ms.tourist_app.application.mapper.DestinationTypeMapper;
import com.ms.tourist_app.application.output.type_destination.DestinationTypeDataOutput;
import com.ms.tourist_app.application.service.DestinationTypeService;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestApiV1
public class DestinationTypeController {
    private final DestinationTypeMapper typeMapper = Mappers.getMapper(DestinationTypeMapper.class);
    private final DestinationTypeService typeService;

    public DestinationTypeController(DestinationTypeService typeService) {
        this.typeService = typeService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping(UrlConst.DestinationType.destinationType)
    public ResponseEntity<?> createDestinationType(@Valid @RequestBody DestinationTypeDataParameter parameter) {
        DestinationTypeDataInput input = typeMapper.createDestination(parameter);
        DestinationTypeDataOutput output = typeService.createDestinationType(input);
        return ResponseUtil.restSuccess(output);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(UrlConst.DestinationType.destinationType)
    public ResponseEntity<?> getAllDestinationType(@Valid GetListDestinationTypeParameter parameter) {
        GetListDestinationTypeInput input = new GetListDestinationTypeInput(parameter.getPage(), parameter.getSize(), parameter.getKeyword());
        List<DestinationTypeDataOutput> outputs = typeService.getListDestinationType(input);
        return ResponseUtil.restSuccess(outputs);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(UrlConst.DestinationType.getDestinationTypeId)
    public ResponseEntity<?> getDestinationTypeDetail(@PathVariable(UrlConst.id) Long id) {
        DestinationTypeDataOutput output = typeService.getDestinationTypeDetail(id);
        return ResponseUtil.restSuccess(output);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping(UrlConst.DestinationType.getDestinationTypeId)
    public ResponseEntity<?> updateDestinationTypeDetail(@PathVariable(UrlConst.id) Long id, @Valid @RequestBody DestinationTypeDataParameter parameter) {
        DestinationTypeDataInput input = typeMapper.createDestination(parameter);
        DestinationTypeDataOutput output = typeService.editDestinationType(id, input);
        return ResponseUtil.restSuccess(output);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping(UrlConst.DestinationType.getDestinationTypeId)
    public ResponseEntity<?> deleteDestinationType(@PathVariable(UrlConst.id) Long id) {
        DestinationTypeDataOutput output = typeService.deleteDestinationType(id);
        return ResponseUtil.restSuccess(output);
    }

}
