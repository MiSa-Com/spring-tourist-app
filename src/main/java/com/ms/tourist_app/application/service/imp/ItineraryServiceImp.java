package com.ms.tourist_app.application.service.imp;

import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.itineraries.FindBestItineraryFromHotelParameter;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.itineraries.ItineraryDataParameter;
import com.ms.tourist_app.application.constants.AppConst;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.application.dai.DestinationRepository;
import com.ms.tourist_app.application.dai.HotelRepository;
import com.ms.tourist_app.application.dai.ItineraryRepository;
import com.ms.tourist_app.application.dai.UserRepository;
import com.ms.tourist_app.application.input.destinations.GetListDestinationCenterRadiusInput;
import com.ms.tourist_app.application.input.itineraries.FindBestItineraryFromHotelInput;
import com.ms.tourist_app.application.input.itineraries.ItineraryDataInput;
import com.ms.tourist_app.application.mapper.ItineraryMapper;
import com.ms.tourist_app.application.output.itineraries.FindBestItineraryFromHotelOutput;
import com.ms.tourist_app.application.output.itineraries.ItineraryDataOutput;
import com.ms.tourist_app.application.output.itineraries.RecommendItineraryOutput;
import com.ms.tourist_app.application.utils.tsp.CompleteGraph;
import com.ms.tourist_app.application.utils.tsp.TSP1;
import com.ms.tourist_app.application.utils.GoogleMapApi;
import com.ms.tourist_app.config.exception.NotFoundException;
import com.ms.tourist_app.domain.entity.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import com.ms.tourist_app.application.service.ItineraryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItineraryServiceImp implements ItineraryService {
    private final ItineraryRepository itineraryRepository;
    private final DestinationRepository destinationRepository;

    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    private final ItineraryMapper itineraryMapper = Mappers.getMapper(ItineraryMapper.class);

    public ItineraryServiceImp(ItineraryRepository itineraryRepository, DestinationRepository destinationRepository,
                               HotelRepository hotelRepository, UserRepository userRepository) {
        this.itineraryRepository = itineraryRepository;
        this.destinationRepository = destinationRepository;
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
    }

    @Override
    public FindBestItineraryFromHotelOutput findBestItineraryFromHotel(FindBestItineraryFromHotelInput findBestItineraryFromHotelInput) {
        TSP1 tsp = new TSP1();
        List<Destination> listDestination = new ArrayList<>();
        String[] listIds = findBestItineraryFromHotelInput.getItinerary().split(",");
        Long idHotel = Long.parseLong(listIds[0]);
        Optional<Hotel> optionalHotel = hotelRepository.findById(idHotel);
        if (optionalHotel.isEmpty()){
            throw new NotFoundException(AppStr.Hotel.tableHotel+AppStr.Base.whiteSpace+AppStr.Exception.notFound);
        }
        Hotel hotel = optionalHotel.get();
        List<Long> listIdDestination = new ArrayList<>();
        for (int i = 1 ; i < listIds.length; i++) {
            listIdDestination.add(Long.parseLong(listIds[i]));
            Optional<Destination> destination = destinationRepository.findById(Long.parseLong(listIds[i]));
            if (destination.isEmpty()){
                throw new NotFoundException(AppStr.Destination.tableDestination+AppStr.Base.whiteSpace+AppStr.Exception.notFound);
            }
            listDestination.add(destination.get());
        }
        List<Address> listAddress = new ArrayList<>();
        listAddress.add(hotel.getAddress());
        for (Destination destination : listDestination) {
            listAddress.add(destination.getAddress());
        }

        CompleteGraph graph = new CompleteGraph(listAddress, findBestItineraryFromHotelInput.getTravelMode());
        tsp.searchSolution(AppConst.TSP.TIME_LIMIT, graph);
        List<Destination> listOutputDestination = new ArrayList<>();
        List<Double> listTime = new ArrayList<>();
        List<Double> listDistance = new ArrayList<>();
        int indexSol = tsp.getSolution(0);
        int indexDes = tsp.getSolution(1);

        Double time = graph.getCost(indexSol, indexDes, false);
        Double distance = graph.getCost(indexSol, indexDes, true);
        listTime.add(time);
        listDistance.add(distance);

        for (int i = 1; i < graph.getNbVertices(); i++) {
            indexSol = tsp.getSolution(i);
            Destination destSol = listDestination.get(indexSol-1);
            listOutputDestination.add( destSol );

            indexDes = 0;
            if (i != graph.getNbVertices() - 1) {
                indexDes = tsp.getSolution(i + 1);
            }

            time = graph.getCost(indexSol, indexDes, false);
            distance = graph.getCost(indexSol, indexDes, true);
            listTime.add(time);
            listDistance.add(distance);
        }
        FindBestItineraryFromHotelOutput findBestItineraryFromHotelOutput = new FindBestItineraryFromHotelOutput(hotel, listOutputDestination,
                                findBestItineraryFromHotelInput.getTravelMode(), listTime, listDistance);
        return findBestItineraryFromHotelOutput;
    }

    @Override
    public RecommendItineraryOutput recommendItinerary(GetListDestinationCenterRadiusInput input, String travelMode) {
        List<Destination> allDestinations = destinationRepository.findAll();
        int maxResult = input.getMaxResult();
        if (maxResult == 0) {
            maxResult = allDestinations.size();
        }
        List<Destination> searchDestinations = new ArrayList<>();
        LatLng center = GoogleMapApi.getLatLng(input.getKeyword());
        for (int i = 0 ; i < allDestinations.size() && i < maxResult ; i++) {
            if ( i < input.getPage() * input.getSize() ) {
                continue;
            }
            if ( i >= (input.getPage()+1) * input.getSize() ) {
                break;
            }
            LatLng latLngDest = new LatLng(allDestinations.get(i).getAddress().getLatitude(), allDestinations.get(i).getAddress().getLongitude());
            double distance = GoogleMapApi.getFlightDistanceInKm(center, latLngDest);
            if (distance <= input.getRadius()) {
                searchDestinations.add(allDestinations.get(i));
            }
        }
        Address addressCenter = new Address();
        addressCenter.setOther(input.getKeyword());
        addressCenter.setLatitude(center.lat);
        addressCenter.setLongitude(center.lng);
        List<Address> listAddress = new ArrayList<>();
        listAddress.add(addressCenter);
        for (Destination destination : searchDestinations) {
            listAddress.add(destination.getAddress());
        }
        TSP1 tsp = new TSP1();
        TravelMode travelModeEnum = TravelMode.UNKNOWN;
        switch (travelMode) {
            case "driving":
                travelModeEnum = TravelMode.DRIVING;
                break;
            case "walking":
                travelModeEnum = TravelMode.WALKING;
                break;
            case "bicycling":
                travelModeEnum = TravelMode.BICYCLING;
                break;
            case "transit":
                travelModeEnum = TravelMode.TRANSIT;
                break;
        }

        CompleteGraph graph = new CompleteGraph(listAddress, travelModeEnum);
        tsp.searchSolution(AppConst.TSP.TIME_LIMIT, graph);
        List<Destination> listOutputDestination = new ArrayList<>();
        List<Double> listTime = new ArrayList<>();
        List<Double> listDistance = new ArrayList<>();
        int indexSol = tsp.getSolution(0);
        int indexDes = tsp.getSolution(1);

        Double time = graph.getCost(indexSol, indexDes, false);
        Double distance = graph.getCost(indexSol, indexDes, true);
        listTime.add(time);
        listDistance.add(distance);

        for (int i = 1; i < graph.getNbVertices(); i++) {
            indexSol = tsp.getSolution(i);
            Destination destSol = searchDestinations.get(indexSol-1);
            listOutputDestination.add( destSol );

            indexDes = 0;
            if (i != graph.getNbVertices() - 1) {
                indexDes = tsp.getSolution(i + 1);
            }

            time = graph.getCost(indexSol, indexDes, false);
            distance = graph.getCost(indexSol, indexDes, true);
            listTime.add(time);
            listDistance.add(distance);
        }
        RecommendItineraryOutput recommendItineraryOutput = new RecommendItineraryOutput(addressCenter, listOutputDestination,
                                listTime, listDistance, travelModeEnum);
        return recommendItineraryOutput;
    }

    @Override
    public ItineraryDataOutput saveItinerary(ItineraryDataInput itineraryDataInput) {
        Optional<User> optionalUser = userRepository.findById(itineraryDataInput.getIdUser());
        if (optionalUser.isEmpty()){
            throw new NotFoundException(AppStr.User.tableUser+AppStr.Base.whiteSpace+AppStr.Exception.notFound);
        }
        User user = optionalUser.get();
        Itinerary itinerary = new Itinerary(itineraryDataInput.getItinerary(), user, itineraryDataInput.getTravelMode());
        itineraryRepository.save(itinerary);
        ItineraryDataOutput itineraryDataOutput = itineraryMapper.toItineraryDataOutput(itinerary);
        return itineraryDataOutput;
    }

    @Override
    public FindBestItineraryFromHotelInput createItineraryInput(FindBestItineraryFromHotelParameter parameter) {
        String listIds = parameter.getIdHotel() + ",";
        for (Long idDest : parameter.getListIdDestination()) {
            listIds += (idDest + ",");
        }
        FindBestItineraryFromHotelInput findBestItineraryFromHotelInput = new FindBestItineraryFromHotelInput();
        findBestItineraryFromHotelInput.setItinerary(listIds);
        String travelMode = parameter.getTravelMode();
        switch (travelMode) {
            case "driving":
                findBestItineraryFromHotelInput.setTravelMode(TravelMode.DRIVING);
                break;
            case "walking":
                findBestItineraryFromHotelInput.setTravelMode(TravelMode.WALKING);
                break;
            case "bicycling":
                findBestItineraryFromHotelInput.setTravelMode(TravelMode.BICYCLING);
                break;
            case "transit":
                findBestItineraryFromHotelInput.setTravelMode(TravelMode.TRANSIT);
                break;
            default:
                findBestItineraryFromHotelInput.setTravelMode(TravelMode.UNKNOWN);
                break;
        }
        return findBestItineraryFromHotelInput;
    }

    @Override
    public ItineraryDataInput toItineraryDataInput(ItineraryDataParameter parameter, Long idUser) {
        String itinerary = "Hotel " + parameter.getIdHotel() + " - " + parameter.getListTime().get(0) + " - " +
                                    parameter.getListDistance().get(0) + " | ";
        for (int i = 0 ; i < parameter.getListIdDestination().size() ; i++) {
            itinerary += (parameter.getListIdDestination().get(i) + " - " + parameter.getListTime().get(i+1) +
                            " - " + parameter.getListDistance().get(i+1) + " | ");
        }
        ItineraryDataInput itineraryDataInput = new ItineraryDataInput();
        itineraryDataInput.setIdUser(idUser);
        itineraryDataInput.setItinerary(itinerary);
        String travelMode = parameter.getTravelMode();
        switch (travelMode) {
            case "driving":
                itineraryDataInput.setTravelMode(TravelMode.DRIVING);
                break;
            case "walking":
                itineraryDataInput.setTravelMode(TravelMode.WALKING);
                break;
            case "bicycling":
                itineraryDataInput.setTravelMode(TravelMode.BICYCLING);
                break;
            case "transit":
                itineraryDataInput.setTravelMode(TravelMode.TRANSIT);
                break;
            default:
                itineraryDataInput.setTravelMode(TravelMode.UNKNOWN);
                break;
        }
        return itineraryDataInput;
    }
}
