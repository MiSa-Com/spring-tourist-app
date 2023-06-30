package com.ms.tourist_app.application.mapper;

import com.ms.tourist_app.adapter.web.v1.transfer.parameter.provinces.ProvinceDataParameter;
import com.ms.tourist_app.application.input.provinces.ProvinceDataInput;
import com.ms.tourist_app.application.output.provinces.ProvinceDataOutput;
import com.ms.tourist_app.domain.entity.Province;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProvinceMapper {

    @Mappings({
            @Mapping(target = "name",source = "input.name"),
            @Mapping(target = "id",source = "id")
    })
    Province toProvince(ProvinceDataInput input,Long id);

    ProvinceDataInput toProvinceDataInput(ProvinceDataParameter parameter);

    @Mappings({
            @Mapping(target = "id",source = "id"),
            @Mapping(target = "name",source = "name"),
            @Mapping(target = "divisionType",source = "divisionType"),
            @Mapping(target = "longitude",source = "longitude"),
            @Mapping(target = "latitude",source = "latitude"),
            @Mapping(target = "nameShort",source = "slugWithoutSpace")
    })
    ProvinceDataOutput toProvinceDataOutput(Province province);
}
