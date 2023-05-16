package com.ms.tourist_app.application.output.itineraries;

import com.google.maps.model.TravelMode;
import com.ms.tourist_app.application.output.destinations.DestinationDataOutput;
import com.ms.tourist_app.application.output.hotels.HotelDataOutput;
import com.ms.tourist_app.domain.entity.Destination;
import com.ms.tourist_app.domain.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindBestItineraryFromHotelOutput {
    private HotelDataOutput hotelDataOutput;
    private List<DestinationDataOutput> listDestinationDataOutput;
    private TravelMode travelMode;
    private List<Double> listTime;
    private List<Double> listDistance;
}
