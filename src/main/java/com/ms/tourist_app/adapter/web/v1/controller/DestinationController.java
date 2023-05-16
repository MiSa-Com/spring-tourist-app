package com.ms.tourist_app.adapter.web.v1.controller;

import com.ms.tourist_app.adapter.web.base.ResponseUtil;
import com.ms.tourist_app.adapter.web.base.RestApiV1;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.destinations.*;
import com.ms.tourist_app.application.constants.UrlConst;
import com.ms.tourist_app.application.input.destinations.*;
import com.ms.tourist_app.application.mapper.DestinationMapper;
import com.ms.tourist_app.application.output.destinations.CommentDestinationDataOutput;
import com.ms.tourist_app.application.output.destinations.DestinationDataOutput;
import com.ms.tourist_app.application.output.destinations.GetListDestinationCenterRadiusOutput;
import com.ms.tourist_app.application.service.DestinationService;
import com.ms.tourist_app.application.utils.JwtUtil;
import com.ms.tourist_app.domain.entity.id.CommentDestinationId;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.Valid;
import java.util.List;

@RestApiV1
public class DestinationController {
    private final DestinationMapper destinationMapper = Mappers.getMapper(DestinationMapper.class);
    private final DestinationService destinationService;
    private final JwtUtil jwtUtil;

    public DestinationController(DestinationService destinationService, JwtUtil jwtUtil) {
        this.destinationService = destinationService;
        this.jwtUtil = jwtUtil;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping(UrlConst.Destination.destination)
    public ResponseEntity<?> createDestination(@Valid DestinationDataParameter parameter) {
        DestinationDataInput dataInput = destinationMapper.createDestinationInput(parameter);
        DestinationDataOutput dataOutput = destinationService.createDestination(dataInput);
        return ResponseUtil.restSuccess(dataOutput);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping(UrlConst.Destination.getDestinationId)
    public ResponseEntity<?> editDestination(@PathVariable(UrlConst.id) Long id, @Valid DestinationDataParameter parameter) {
        DestinationDataInput dataInput = destinationMapper.createDestinationInput(parameter);
        DestinationDataOutput dataOutput = destinationService.editDestination(dataInput, id);
        return ResponseUtil.restSuccess(dataOutput);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(UrlConst.Destination.destinationByUser)
    public ResponseEntity<?> getListDestinationByUser(@PathVariable(UrlConst.id) Long id) {
        List<DestinationDataOutput> dataOutputs = destinationService.getListDestinationByCreateBy(id);
        return ResponseUtil.restSuccess(dataOutputs);

    }

    @GetMapping(UrlConst.Destination.getDestinationId)
    public ResponseEntity<?> viewDestinationDetail(@PathVariable("id") Long idDestination) {
        DestinationDataOutput destinationDataOutput = destinationService.getDestinationDetail(idDestination);
        return ResponseUtil.restSuccess(destinationDataOutput);
    }

    @GetMapping(UrlConst.Destination.destinationFilter)
    public ResponseEntity<?> getListDestinationByProvince(@Valid GetListDestinationByProvinceParameter parameter) {
        GetListDestinationByProvinceInput input = new GetListDestinationByProvinceInput(parameter.getPage(), parameter.getSize(), parameter.getIdProvince());
        List<DestinationDataOutput> outputs = destinationService.getListDestinationByProvince(input);
        return ResponseUtil.restSuccess(outputs);
    }

    @GetMapping(UrlConst.Destination.destination)
    public ResponseEntity<?> getListDestinationFilter(@Valid GetListDestinationByKeywordParameter parameter) {
        GetListDestinationByKeywordInput input = new GetListDestinationByKeywordInput(parameter.getKeyword(), parameter.getPage(), parameter.getSize());
        List<DestinationDataOutput> outputs = destinationService.getListDestinationByKeyword(input);
        return ResponseUtil.restSuccess(outputs);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(UrlConst.Destination.destinationRadius)
    public ResponseEntity<?> getListDestinationCenterRadius(@Valid GetListDestinationCenterRadiusParameter parameter) {
        GetListDestinationCenterRadiusInput input = new GetListDestinationCenterRadiusInput(parameter.getPage(),parameter.getSize(), parameter.getKeyword(),
                                            parameter.getRadius());
        GetListDestinationCenterRadiusOutput outputs = destinationService.getListDestinationCenterRadius(input);
        return ResponseUtil.restSuccess(outputs);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping(UrlConst.Destination.commentDestination)
    public ResponseEntity<?> createCommentDestination(@Valid CommentDestinationDataParameter parameter) {
        CommentDestinationDataInput input = new CommentDestinationDataInput(parameter.getIdDestination(), parameter.getContent(), parameter.getRating());
        CommentDestinationDataOutput output = destinationService.createComment(input.getIdDestination(), input);
        return ResponseUtil.restSuccess(output);
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping(UrlConst.Destination.commentDestination)
    public ResponseEntity<?> editCommentDestination(@Valid CommentDestinationDataParameter parameter) {
        CommentDestinationDataInput input = new CommentDestinationDataInput(parameter.getIdDestination(), parameter.getContent(), parameter.getRating());
        CommentDestinationDataOutput output = destinationService.editComment(new CommentDestinationId(jwtUtil.getUserIdFromToken(), input.getIdDestination()), input);
        return ResponseUtil.restSuccess(output);
    }

    @GetMapping(UrlConst.Destination.topDestination)
    public ResponseEntity<?> getListDestinationNearest(@Valid SelectTopCreateAtParameter parameter) {
        SelectTopCreateAtInput input = new SelectTopCreateAtInput(parameter.getPage(), parameter.getSize());
        List<DestinationDataOutput> outputs = destinationService.selectTopCreateAt(input);
        return ResponseUtil.restSuccess(outputs);
    }
}
