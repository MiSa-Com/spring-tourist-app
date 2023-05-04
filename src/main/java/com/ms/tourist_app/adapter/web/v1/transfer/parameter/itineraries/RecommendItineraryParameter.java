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
public class RecommendItineraryParameter {
    private String address;
    private String travelMode;
    private Integer maxDestination;
}
