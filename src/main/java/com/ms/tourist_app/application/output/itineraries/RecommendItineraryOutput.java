package com.ms.tourist_app.application.output.itineraries;

import com.google.maps.model.TravelMode;
import com.ms.tourist_app.application.output.addresses.AddressDataOutput;
import com.ms.tourist_app.application.output.destinations.DestinationDataOutput;
import com.ms.tourist_app.domain.entity.Address;
import com.ms.tourist_app.domain.entity.Destination;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendItineraryOutput {
    private AddressDataOutput addressDataOutput;
    private List<DestinationDataOutput> listDestinationDataOutput;
    private List<Double> listTime;
    private List<Double> listDistance;
    private TravelMode travelMode;
}
