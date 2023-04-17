package com.ms.tourist_app.application.service;

import com.ms.tourist_app.adapter.web.v1.transfer.parameter.hotels.HotelDataParameter;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.itineraries.FindBestItineraryFromHotelParameter;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.itineraries.ItineraryDataParameter;
import com.ms.tourist_app.application.input.destinations.GetListDestinationCenterRadiusInput;
import com.ms.tourist_app.application.input.itineraries.FindBestItineraryFromHotelInput;
import com.ms.tourist_app.application.input.itineraries.ItineraryDataInput;
import com.ms.tourist_app.application.output.itineraries.FindBestItineraryFromHotelOutput;
import com.ms.tourist_app.application.output.itineraries.ItineraryDataOutput;
import com.ms.tourist_app.application.output.itineraries.RecommendItineraryOutput;

public interface ItineraryService {
    FindBestItineraryFromHotelOutput findBestItineraryFromHotel(FindBestItineraryFromHotelInput findBestItineraryFromHotelInput);
    ItineraryDataOutput saveItinerary(ItineraryDataInput itineraryDataInput);
    FindBestItineraryFromHotelInput createItineraryInput(FindBestItineraryFromHotelParameter parameter);
    ItineraryDataInput toItineraryDataInput(ItineraryDataParameter parameter, Long idUser);
    RecommendItineraryOutput recommendItinerary(GetListDestinationCenterRadiusInput input, String travelMode);
}
