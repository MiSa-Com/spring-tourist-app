package com.ms.tourist_app.application.service.imp;

import com.google.maps.model.TravelMode;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.application.dai.DestinationRepository;
import com.ms.tourist_app.application.dai.RoadRepository;
import com.ms.tourist_app.application.dai.UserRepository;
import com.ms.tourist_app.application.input.roads.RoadDataInput;
import com.ms.tourist_app.application.mapper.RoadMapper;
import com.ms.tourist_app.application.output.roads.RoadDataOutput;
import com.ms.tourist_app.application.service.tsp.CompleteGraph;
import com.ms.tourist_app.application.service.tsp.TSP1;
import com.ms.tourist_app.config.exception.NotFoundException;
import com.ms.tourist_app.domain.entity.Destination;
import com.ms.tourist_app.domain.entity.Road;
import com.ms.tourist_app.domain.entity.User;
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

    private final UserRepository userRepository;

    private final RoadMapper roadMapper = Mappers.getMapper(RoadMapper.class);

    public RoadServiceImp(RoadRepository roadRepository, DestinationRepository destinationRepository, UserRepository userRepository) {
        this.roadRepository = roadRepository;
        this.destinationRepository = destinationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RoadDataOutput createRoad(RoadDataInput roadDataInput) {
        TSP1 tsp = new TSP1();
        List<Destination> listDestination = new ArrayList<>();
        String[] listIds = roadDataInput.getRoad().split(",");
        List<Long> listIdDestination = new ArrayList<>();
        for (String id : listIds) {
            listIdDestination.add(Long.parseLong(id));
        }
        for (int i = 0; i < listIdDestination.size(); i++) {
            Optional<Destination> destination = destinationRepository.findById(listIdDestination.get(i));
            if (destination.isEmpty()){
                throw new NotFoundException(AppStr.Destination.tableDestination+AppStr.Base.whiteSpace+AppStr.Exception.notFound);
            }
            listDestination.add(destination.get());
        }
        CompleteGraph graph = new CompleteGraph(listDestination, TravelMode.WALKING, true);
        tsp.searchSolution(5000, graph);
        String resultRoad = "";
        for (int i = 0; i < graph.getNbVertices(); i++) {
            resultRoad += listDestination.get( tsp.getSolution(i) ).getId() + " - ";
            int indexOrigin = listDestination.indexOf( listDestination.get(tsp.getSolution(i)) );
            int indexDestination = 0;
            if (i != graph.getNbVertices() - 1) {
                indexDestination = listDestination.indexOf( listDestination.get(tsp.getSolution(i + 1)) );
            }

            resultRoad += graph.getCost(indexOrigin, indexDestination) + " | ";
        }

        resultRoad += "Total time in minute: " + tsp.getSolutionCost();

        Optional<User> user = userRepository.findById(roadDataInput.getIdUser());
        if (user.isEmpty()){
            throw new NotFoundException(AppStr.User.tableUser+AppStr.Base.whiteSpace+AppStr.Exception.notFound);
        }
        Road road = new Road(resultRoad, user.get());
        roadRepository.save(road);
        RoadDataOutput roadDataOutput = roadMapper.toRoadDataOutput(road);
        return roadDataOutput;
    }

}
