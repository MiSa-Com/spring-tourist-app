package com.ms.tourist_app.application.service;

import com.ms.tourist_app.application.input.type_destination.DestinationTypeDataInput;
import com.ms.tourist_app.application.input.type_destination.GetListDestinationTypeInput;
import com.ms.tourist_app.application.output.type_destination.DestinationTypeDataOutput;

import java.util.List;

public interface DestinationTypeService {
    List<DestinationTypeDataOutput> getListDestinationType(GetListDestinationTypeInput input);
    DestinationTypeDataOutput getDestinationTypeDetail(Long id);
    DestinationTypeDataOutput createDestinationType(DestinationTypeDataInput input);
    DestinationTypeDataOutput editDestinationType(Long id, DestinationTypeDataInput input);
    DestinationTypeDataOutput deleteDestinationType(Long id);

}
