package com.ms.tourist_app.adapter.web.v1.transfer.parameter.roads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindBestRoadFromHotelParameter {
    private Long idHotel;
    private List<Long> listIdDestination;

}
