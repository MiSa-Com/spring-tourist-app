package com.ms.tourist_app.application.service;

import com.ms.tourist_app.application.input.destinations.DestinationDataInput;
import com.ms.tourist_app.application.input.destinations.GetListDestinationCenterRadiusInput;
import com.ms.tourist_app.application.input.destinations.GetListDestinationInput;
import com.ms.tourist_app.application.output.destinations.DestinationDataOutput;

import java.util.List;

public interface DestinationService {
    DestinationDataOutput getDestinationDetail(Long id);
    List<DestinationDataOutput> getListDestination(GetListDestinationInput input);
    List<DestinationDataOutput> getListDestinationCenterRadius(GetListDestinationCenterRadiusInput input);
    DestinationDataOutput createDestination(DestinationDataInput input);
    DestinationDataInput editDestination(DestinationDataInput input,Long id);
    DestinationDataOutput deleteDestination(Long id);
}
