package com.ms.tourist_app.application.input.itineraries;

import com.google.maps.model.TravelMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryDataInput {
    private String itinerary;
    private Long idUser;
    private TravelMode travelMode;
}
