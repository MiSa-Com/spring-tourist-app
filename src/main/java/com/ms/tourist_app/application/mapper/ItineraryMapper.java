package com.ms.tourist_app.application.mapper;

import com.ms.tourist_app.application.output.itineraries.ItineraryDataOutput;
import com.ms.tourist_app.domain.entity.Itinerary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ItineraryMapper {
    @Mappings({
        @Mapping(target = "itinerary", source = "itinerary.itinerary"),
        @Mapping(target = "user", source = "itinerary.user"),
        @Mapping(target = "travelMode", source = "itinerary.travelMode")
    })
    ItineraryDataOutput toItineraryDataOutput(Itinerary itinerary);

}
