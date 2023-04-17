package com.ms.tourist_app.application.output.itineraries;

import com.google.maps.model.TravelMode;
import com.ms.tourist_app.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryDataOutput {
    private String itinerary;
    private User user;
    private TravelMode travelMode;
}

