package com.ms.tourist_app.application.mapper;

import com.ms.tourist_app.adapter.web.v1.transfer.parameter.destinations.DestinationDataParameter;
import com.ms.tourist_app.application.input.destinations.DestinationDataInput;
import com.ms.tourist_app.application.output.destinations.DestinationDataOutput;
import com.ms.tourist_app.domain.entity.Destination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface DestinationMapper {

    @Mappings({
            @Mapping(target = "name",source = "input.name"),
            @Mapping(target = "description",source = "input.description"),
            @Mapping(target = "id",source = "id")
    })
    Destination toDestination(DestinationDataInput input, Long id);

    DestinationDataInput createDestinationInput(DestinationDataParameter parameter);

    @Mappings({
            @Mapping(target = "id",source = "id"),
            @Mapping(target = "name",source = "name"),
            @Mapping(target = "description",source = "description")
    })
    DestinationDataOutput toDestinationDataOutput(Destination destination);


}
