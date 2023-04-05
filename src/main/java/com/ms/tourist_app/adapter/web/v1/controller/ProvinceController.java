package com.ms.tourist_app.adapter.web.v1.controller;

import com.cloudinary.Url;
import com.ms.tourist_app.adapter.web.base.ResponseUtil;
import com.ms.tourist_app.adapter.web.base.RestApiV1;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.provinces.GetListProvinceDataParameter;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.provinces.ProvinceDataParameter;
import com.ms.tourist_app.application.constants.UrlConst;
import com.ms.tourist_app.application.input.provinces.GetListProvinceDataInput;
import com.ms.tourist_app.application.input.provinces.ProvinceDataInput;
import com.ms.tourist_app.application.mapper.ProvinceMapper;
import com.ms.tourist_app.application.output.provinces.ProvinceDataOutput;
import com.ms.tourist_app.application.service.ProvinceService;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestApiV1
public class ProvinceController {

    private final ProvinceMapper provinceMapper = Mappers.getMapper(ProvinceMapper.class);
    private final ProvinceService provinceService;

    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @PostMapping(UrlConst.Province.province)
    public ResponseEntity<?> createProvince(@Valid @RequestBody ProvinceDataParameter parameter){
        ProvinceDataInput provinceDataInput = provinceMapper.toProvinceDataInput(parameter);
        ProvinceDataOutput provinceDataOutput = provinceService.createProvince(provinceDataInput);
        return ResponseUtil.restSuccess(provinceDataOutput);
    }

    @GetMapping(UrlConst.Province.province)
    public ResponseEntity<?> getListProvince(@Valid GetListProvinceDataParameter parameter){
        GetListProvinceDataInput dataInput = new GetListProvinceDataInput(parameter.getKeyword(),parameter.getPage(),parameter.getSize());
        List<ProvinceDataOutput> output = provinceService.getListProvinceDataOutput(dataInput);
        return ResponseUtil.restSuccess(output);
    }

    @PutMapping(UrlConst.Province.getProvinceById)
    public ResponseEntity<?> editProvince(@PathVariable(UrlConst.id)Long id,@Valid @RequestBody ProvinceDataParameter parameter){
        ProvinceDataInput provinceDataInput = provinceMapper.toProvinceDataInput(parameter);
        ProvinceDataOutput provinceDataOutput = provinceService.editProvince(id,provinceDataInput);
        return ResponseUtil.restSuccess(provinceDataOutput);
    }

    @DeleteMapping(UrlConst.Province.getProvinceById)
    public ResponseEntity<?> deleteProvince(@PathVariable(UrlConst.id)Long id){
        ProvinceDataOutput provinceDataOutput = provinceService.deleteProvince(id);
        return ResponseUtil.restSuccess(provinceDataOutput);
    }
}
