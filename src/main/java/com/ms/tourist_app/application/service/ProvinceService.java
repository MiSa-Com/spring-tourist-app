package com.ms.tourist_app.application.service;

import com.ms.tourist_app.adapter.web.v1.transfer.parameter.provinces.GetListProvinceDataParameter;
import com.ms.tourist_app.application.input.provinces.GetListProvinceDataInput;
import com.ms.tourist_app.application.input.provinces.ProvinceDataInput;
import com.ms.tourist_app.application.output.provinces.ProvinceDataOutput;

import java.util.List;

public interface ProvinceService {
    ProvinceDataOutput createProvince(ProvinceDataInput provinceDataInput);
    List<ProvinceDataOutput> getListProvinceDataOutput(GetListProvinceDataInput getListProvinceDataInput);
    ProvinceDataOutput editProvince(Long id,ProvinceDataInput provinceDataInput);
    ProvinceDataOutput deleteProvince(Long id);
    Long getProvinceByCoordinate(Double lon,Double lat);
}
