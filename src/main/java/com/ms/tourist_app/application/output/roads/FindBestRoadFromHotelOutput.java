package com.ms.tourist_app.application.output.roads;

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
public class FindBestRoadFromHotelOutput {
    private Hotel hotel;
    private List<Destination> listDestination;
    private List<Double> listTime;
}
