package com.ms.tourist_app.application.service.imp;

import com.google.maps.model.TravelMode;
import com.ms.tourist_app.application.constants.AppConst;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.application.dai.DestinationRepository;
import com.ms.tourist_app.application.dai.HotelRepository;
import com.ms.tourist_app.application.dai.RoadRepository;
import com.ms.tourist_app.application.dai.UserRepository;
import com.ms.tourist_app.application.input.roads.FindBestRoadFromHotelInput;
import com.ms.tourist_app.application.input.roads.RoadDataInput;
import com.ms.tourist_app.application.mapper.RoadMapper;
import com.ms.tourist_app.application.output.roads.FindBestRoadFromHotelOutput;
import com.ms.tourist_app.application.output.roads.RoadDataOutput;
import com.ms.tourist_app.application.service.tsp.CompleteGraph;
import com.ms.tourist_app.application.service.tsp.TSP1;
import com.ms.tourist_app.config.exception.NotFoundException;
import com.ms.tourist_app.domain.entity.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import  com.ms.tourist_app.application.service.RoadService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoadServiceImp implements RoadService {
    private final RoadRepository roadRepository;
    private final DestinationRepository destinationRepository;

    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    private final RoadMapper roadMapper = Mappers.getMapper(RoadMapper.class);

    public RoadServiceImp(RoadRepository roadRepository, DestinationRepository destinationRepository,
                          HotelRepository hotelRepository, UserRepository userRepository) {
        this.roadRepository = roadRepository;
        this.destinationRepository = destinationRepository;
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
    }

    @Override
    public FindBestRoadFromHotelOutput findBestRoadFromHotel(FindBestRoadFromHotelInput findBestRoadFromHotelInput) {
        TSP1 tsp = new TSP1();
        List<Destination> listDestination = new ArrayList<>();
        String[] listIds = findBestRoadFromHotelInput.getRoad().split(",");
        Long idHotel = Long.parseLong(listIds[0]);
        Hotel hotel = hotelRepository.findById(idHotel).get();
        List<Long> listIdDestination = new ArrayList<>();
        for (int i = 1 ; i < listIds.length; i++) {
            listIdDestination.add(Long.valueOf(listIds[i]));
            Optional<Destination> destination = destinationRepository.findById(Long.valueOf(listIds[i]));
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

        CompleteGraph graph = new CompleteGraph(listAddress, TravelMode.WALKING, true);
        tsp.searchSolution(AppConst.TSP.TIME_LIMIT, graph);
        List<Destination> listOutputDestination = new ArrayList<>();
        List<Double> listTime = new ArrayList<>();
        int indexSol = tsp.getSolution(0);
        int indexDes = tsp.getSolution(1);
        Destination secondDestination = listDestination.get(indexDes-1);
        Double time = graph.getCost(indexSol, indexDes);
        listTime.add(time);

        for (int i = 1; i < graph.getNbVertices(); i++) {
            indexSol = tsp.getSolution(i);
            Destination destSol = listDestination.get(indexSol-1);
            listOutputDestination.add( destSol );

            indexDes = 0;
            if (i != graph.getNbVertices() - 1) {
                indexDes = tsp.getSolution(i + 1);
            }

            time = graph.getCost(indexSol, indexDes);
            listTime.add(time);
        }
        FindBestRoadFromHotelOutput findBestRoadFromHotelOutput = new FindBestRoadFromHotelOutput(hotel, listOutputDestination, listTime);
        return findBestRoadFromHotelOutput;
    }

    @Override
    public RoadDataOutput createRoad(RoadDataInput roadDataInput) {
        User user = userRepository.findById(roadDataInput.getIdUser()).get();
        Road road = new Road(roadDataInput.getRoad(), user);
        roadRepository.save(road);
        RoadDataOutput roadDataOutput = roadMapper.toRoadDataOutput(road);
        return roadDataOutput;
    }

}
