package com.ms.tourist_app.application.service;

import com.ms.tourist_app.application.input.destinations.DestinationDataInput;
import com.ms.tourist_app.application.input.destinations.GetListDestinationByKeywordInput;
import com.ms.tourist_app.application.input.destinations.GetListDestinationCenterRadiusInput;
import com.ms.tourist_app.application.input.destinations.GetListDestinationByProvinceInput;
import com.ms.tourist_app.application.output.destinations.DestinationDataOutput;

import java.util.List;

public interface DestinationService {
    DestinationDataOutput getDestinationDetail(Long id);
    List<DestinationDataOutput> getListDestinationByProvince(GetListDestinationByProvinceInput input);
    List<DestinationDataOutput> getListDestinationCenterRadius(GetListDestinationCenterRadiusInput input);
    List<DestinationDataOutput> getListDestinationByKeyword(GetListDestinationByKeywordInput input);
    DestinationDataOutput createDestination(DestinationDataInput input);
    DestinationDataInput editDestination(DestinationDataInput input,Long id);
    DestinationDataOutput deleteDestination(Long id);
}
