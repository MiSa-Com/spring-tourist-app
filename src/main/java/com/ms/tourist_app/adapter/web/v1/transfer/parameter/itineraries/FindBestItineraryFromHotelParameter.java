package com.ms.tourist_app.adapter.web.v1.transfer.parameter.itineraries;

import com.google.maps.model.TravelMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindBestItineraryFromHotelParameter {
    private Long idHotel;
    private List<Long> listIdDestination;
    private String travelMode;

}
