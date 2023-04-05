package com.ms.tourist_app.application.mapper;

import com.ms.tourist_app.application.input.roads.RoadDataInput;
import com.ms.tourist_app.application.output.roads.RoadDataOutput;
import com.ms.tourist_app.domain.entity.Road;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface RoadMapper {
    @Mappings({
        @Mapping(target = "road", source = "road.road"),
        @Mapping(target = "user", source = "road.user")
    })
    RoadDataOutput toRoadDataOutput(Road road);

}
