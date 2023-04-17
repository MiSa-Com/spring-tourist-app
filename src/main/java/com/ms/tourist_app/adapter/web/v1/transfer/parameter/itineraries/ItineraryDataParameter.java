package com.ms.tourist_app.adapter.web.v1.transfer.parameter.itineraries;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryDataParameter {
    private Long idHotel;
    private List<Long> listIdDestination;
    private List<Double> listTime;
    private List<Double> listDistance;
    private String travelMode;
}
