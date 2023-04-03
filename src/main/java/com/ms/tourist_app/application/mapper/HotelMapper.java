package com.ms.tourist_app.application.mapper;

import com.ms.tourist_app.adapter.web.v1.transfer.parameter.hotels.HotelDataParameter;
import com.ms.tourist_app.application.input.hotels.HotelDataInput;
import com.ms.tourist_app.application.output.hotels.HotelDataOutput;
import com.ms.tourist_app.domain.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    @Mappings({
            @Mapping(target = "name",source = "input.name"),
            @Mapping(target = "description",source = "input.description"),
            @Mapping(target = "telephone",source = "input.telephone"),
            @Mapping(target = "id",source = "id")
    })
    Hotel toHotel(HotelDataInput input,Long id);

    HotelDataInput createHotelDataInput(HotelDataParameter parameter);


    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "createBy", source = "createBy"),
            @Mapping(target = "createAt", source = "createAt"),
            @Mapping(target = "updateBy", source = "updateBy"),
            @Mapping(target = "updateAt", source = "updateAt"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "telephone", source = "telephone")
    })
    HotelDataOutput toHotelDataOutput(Hotel hotel);
}
