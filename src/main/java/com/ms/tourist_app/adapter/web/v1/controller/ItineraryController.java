package com.ms.tourist_app.adapter.web.v1.controller;

import com.ms.tourist_app.adapter.web.base.ResponseUtil;
import com.ms.tourist_app.adapter.web.base.RestApiV1;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.itineraries.FindBestItineraryFromHotelParameter;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.itineraries.ItineraryDataParameter;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.itineraries.RecommendItineraryParameter;
import com.ms.tourist_app.application.constants.AppConst;
import com.ms.tourist_app.application.constants.UrlConst;
import com.ms.tourist_app.application.input.destinations.GetListDestinationCenterRadiusInput;
import com.ms.tourist_app.application.input.itineraries.FindBestItineraryFromHotelInput;
import com.ms.tourist_app.application.input.itineraries.ItineraryDataInput;
import com.ms.tourist_app.application.output.destinations.DestinationDataOutput;
import com.ms.tourist_app.application.output.itineraries.FindBestItineraryFromHotelOutput;
import com.ms.tourist_app.application.output.itineraries.ItineraryDataOutput;
import com.ms.tourist_app.application.output.itineraries.RecommendItineraryOutput;
import com.ms.tourist_app.application.service.DestinationService;
import com.ms.tourist_app.application.service.imp.DestinationServiceImp;
import com.ms.tourist_app.application.service.imp.ItineraryServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@RestApiV1
public class ItineraryController {
    private final ItineraryServiceImp itineraryService;

    public ItineraryController(ItineraryServiceImp itineraryService) {
        this.itineraryService = itineraryService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping(UrlConst.Itinerary.getByIdUser)
    public ResponseEntity<?> saveItinerary(@Valid @RequestBody ItineraryDataParameter parameter,
                                        @PathVariable(value = UrlConst.id) Long idUser){
        ItineraryDataInput itineraryDataInput = itineraryService.toItineraryDataInput(parameter, idUser);
        ItineraryDataOutput itineraryDataOutput = itineraryService.saveItinerary(itineraryDataInput);
        return ResponseUtil.restSuccess(itineraryDataOutput);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping(UrlConst.Itinerary.bestItinerary)
    public ResponseEntity<?> findBestItineraryFromHotel(@Valid @RequestBody FindBestItineraryFromHotelParameter parameter) {
        FindBestItineraryFromHotelInput findBestItineraryFromHotelInput = itineraryService.createItineraryInput(parameter);
        FindBestItineraryFromHotelOutput findBestItineraryFromHotelOutput = itineraryService.findBestItineraryFromHotel(findBestItineraryFromHotelInput);
        return ResponseUtil.restSuccess(findBestItineraryFromHotelOutput);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping(UrlConst.Itinerary.recommendItinerary)
    public ResponseEntity<?> recommendItinerary(@Valid @RequestBody RecommendItineraryParameter parameter) {
        GetListDestinationCenterRadiusInput getListDestinationCenterRadiusInput = new GetListDestinationCenterRadiusInput(0, parameter.getMaxDestination(),
                parameter.getAddress(), AppConst.ItineraryController.recommendedRadius);
        RecommendItineraryOutput recommendItineraryOutput = itineraryService.recommendItinerary(getListDestinationCenterRadiusInput, parameter.getTravelMode());
        return ResponseUtil.restSuccess(recommendItineraryOutput);
    }
}
