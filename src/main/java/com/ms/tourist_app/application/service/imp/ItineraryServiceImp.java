package com.ms.tourist_app.application.service.imp;

import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.itineraries.FindBestItineraryFromHotelParameter;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.itineraries.ItineraryDataParameter;
import com.ms.tourist_app.application.constants.AppConst;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.application.dai.*;
import com.ms.tourist_app.application.input.destinations.GetListDestinationCenterRadiusInput;
import com.ms.tourist_app.application.input.itineraries.FindBestItineraryFromHotelInput;
import com.ms.tourist_app.application.input.itineraries.ItineraryDataInput;
import com.ms.tourist_app.application.mapper.AddressMapper;
import com.ms.tourist_app.application.mapper.DestinationMapper;
import com.ms.tourist_app.application.mapper.HotelMapper;
import com.ms.tourist_app.application.mapper.ItineraryMapper;
import com.ms.tourist_app.application.output.addresses.AddressDataOutput;
import com.ms.tourist_app.application.output.destinations.DestinationDataOutput;
import com.ms.tourist_app.application.output.hotels.HotelDataOutput;
import com.ms.tourist_app.application.output.itineraries.FindBestItineraryFromHotelOutput;
import com.ms.tourist_app.application.output.itineraries.ItineraryDataOutput;
import com.ms.tourist_app.application.output.itineraries.RecommendItineraryOutput;
import com.ms.tourist_app.application.utils.JwtUtil;
import com.ms.tourist_app.application.utils.tsp.CompleteGraph;
import com.ms.tourist_app.application.utils.tsp.Graph;
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
import java.util.Random;

@Service
public class ItineraryServiceImp implements ItineraryService {
    private final ItineraryRepository itineraryRepository;
    private final DestinationRepository destinationRepository;
    private final ImageDestinationRepository imageDestinationRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final ItineraryMapper itineraryMapper = Mappers.getMapper(ItineraryMapper.class);
    private final DestinationMapper destinationMapper = Mappers.getMapper(DestinationMapper.class);
    private final AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);
    private final HotelMapper hotelMapper = Mappers.getMapper(HotelMapper.class);

    public ItineraryServiceImp(ItineraryRepository itineraryRepository, DestinationRepository destinationRepository,
                               ImageDestinationRepository imageDestinationRepository, HotelRepository hotelRepository, UserRepository userRepository, JwtUtil jwtUtil) {
        this.itineraryRepository = itineraryRepository;
        this.destinationRepository = destinationRepository;
        this.imageDestinationRepository = imageDestinationRepository;
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    private TravelMode chooseTravelMode(String travelMode) {
        switch (travelMode) {
            case "driving":
                return TravelMode.DRIVING;
            case "walking":
                return TravelMode.WALKING;
            case "bicycling":
                return TravelMode.BICYCLING;
            case "transit":
                return TravelMode.TRANSIT;
            default:
                return TravelMode.UNKNOWN;
        }
    }

    private List<Destination> findItinerarySolution(Graph graph, List<Destination> listDestination, List<Double> listTime, List<Double> listDistance) {
        TSP1 tsp = new TSP1();
        tsp.searchSolution(AppConst.TSP.TIME_LIMIT, graph);
        List<Destination> listOutputDestination = new ArrayList<>();

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
        return listOutputDestination;
    }

    @Override
    public FindBestItineraryFromHotelOutput findBestItineraryFromHotel(FindBestItineraryFromHotelInput findBestItineraryFromHotelInput) {

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
        List<Double> listTime = new ArrayList<>();
        List<Double> listDistance = new ArrayList<>();
        List<Destination> listOutputDestination = findItinerarySolution(graph, listDestination,
                                                                listTime, listDistance);
        HotelDataOutput hotelDataOutput = hotelMapper.toHotelDataOutput(hotel);
        List<DestinationDataOutput> listOutputDestinationDataOutput = new ArrayList<>();
        for (Destination dest : listOutputDestination) {
            DestinationDataOutput destOutput = destinationMapper.toDestinationDataOutput(dest);
            listOutputDestinationDataOutput.add(destOutput);
        }
        FindBestItineraryFromHotelOutput findBestItineraryFromHotelOutput = new FindBestItineraryFromHotelOutput(hotelDataOutput, listOutputDestinationDataOutput,
                                findBestItineraryFromHotelInput.getTravelMode(), listTime, listDistance);
        return findBestItineraryFromHotelOutput;
    }

    @Override
    public RecommendItineraryOutput recommendItinerary(GetListDestinationCenterRadiusInput input, String travelMode) {
        // fix page = 0
        List<Destination> allDestinations = destinationRepository.findAll();

        List<Destination> destinationsInCircle = new ArrayList<>();
        LatLng center = GoogleMapApi.getLatLng(input.getKeyword());
        for (Destination allDestination : allDestinations) {
            LatLng latLngDest = new LatLng(allDestination.getAddress().getLatitude(), allDestination.getAddress().getLongitude());
            double distance = GoogleMapApi.getFlightDistanceInKm(center, latLngDest);
            if (distance <= input.getRadius()) {
                destinationsInCircle.add(allDestination);
            }
        }
        Random rand = new Random();
        List<Destination> searchDestinations = new ArrayList<>();
        int maxResult = input.getSize();
        for (int i = 0; i < maxResult; i++) {
            if (destinationsInCircle.size() == 0) {
                break;
            }
            int randomIndex = rand.nextInt(destinationsInCircle.size());
            searchDestinations.add(destinationsInCircle.get(randomIndex));
            destinationsInCircle.remove(randomIndex);
        }

        Address addressCenter = new Address();
        addressCenter.setDetailAddress(input.getKeyword());
        addressCenter.setLatitude(center.lat);
        addressCenter.setLongitude(center.lng);
        List<Address> listAddress = new ArrayList<>();
        listAddress.add(addressCenter);
        for (Destination destination : searchDestinations) {
            listAddress.add(destination.getAddress());
        }
        TravelMode travelModeEnum = chooseTravelMode(travelMode);

        CompleteGraph graph = new CompleteGraph(listAddress, travelModeEnum);
        List<Double> listTime = new ArrayList<>();
        List<Double> listDistance = new ArrayList<>();
        List<Destination> listOutputDestination = findItinerarySolution(graph, searchDestinations,
                                listTime, listDistance);
        AddressDataOutput addressCenterDataOutput = addressMapper.toAddressDataOutput(addressCenter);
        List<DestinationDataOutput> outputs = new ArrayList<>();
        for (int i = 0; i < listOutputDestination.size(); i++) {
            DestinationDataOutput output = destinationMapper.toDestinationDataOutput(listOutputDestination.get(i));
            output.setDestinationType(listOutputDestination.get(i).getDestinationType());
            output.setAddress(listOutputDestination.get(i).getAddress());
            List<ImageDestination> imageDestinations = imageDestinationRepository.findAllByDestination(listOutputDestination.get(i));
            List<String> imageDestinationOutputs = new ArrayList<>();
            for (ImageDestination imageDestination : imageDestinations) {
                String imageDestinationOutput = imageDestination.getLink();
                imageDestinationOutputs.add(imageDestinationOutput);
            }
            output.setImages(imageDestinationOutputs);
            outputs.add(output);
        }
        RecommendItineraryOutput recommendItineraryOutput = new RecommendItineraryOutput(addressCenterDataOutput, outputs,
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
        if (jwtUtil.getUserIdFromToken()!=null){
            itinerary.setCreateBy(jwtUtil.getUserIdFromToken());
        }
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
