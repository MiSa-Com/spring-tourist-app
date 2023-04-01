package com.ms.tourist_app.application.mapper;

import com.ms.tourist_app.adapter.web.v1.transfer.parameter.destination_type.DestinationTypeDataParameter;
import com.ms.tourist_app.application.input.type_destination.DestinationTypeDataInput;
import com.ms.tourist_app.application.output.type_destination.DestinationTypeDataOutput;
import com.ms.tourist_app.domain.entity.DestinationType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface DestinationTypeMapper {
    @Mappings({
            @Mapping(target = "name",source = "input.name"),
            @Mapping(target = "id",source = "id")
    })
    DestinationType toDestinationType(DestinationTypeDataInput input, Long id);

    DestinationTypeDataInput createDestination(DestinationTypeDataParameter parameter);


    @Mappings({
            @Mapping(target = "id",source = "destinationType.id"),
            @Mapping(target = "name",source = "name")
    })
    DestinationTypeDataOutput toDestinationTypeDataOutput(DestinationType destinationType);

}
