package com.ms.tourist_app.application.service.imp;

import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.application.dai.AddressRepository;
import com.ms.tourist_app.application.dai.DestinationRepository;
import com.ms.tourist_app.application.dai.DestinationTypeRepository;
import com.ms.tourist_app.application.dai.ImageDestinationRepository;
import com.ms.tourist_app.application.input.destinations.DestinationDataInput;
import com.ms.tourist_app.application.input.destinations.GetListDestinationInput;
import com.ms.tourist_app.application.mapper.DestinationMapper;
import com.ms.tourist_app.application.output.destinations.DestinationDataOutput;
import com.ms.tourist_app.application.service.DestinationService;
import com.ms.tourist_app.application.utils.UploadFile;
import com.ms.tourist_app.config.exception.NotFoundException;
import com.ms.tourist_app.domain.entity.Address;
import com.ms.tourist_app.domain.entity.Destination;
import com.ms.tourist_app.domain.entity.DestinationType;
import com.ms.tourist_app.domain.entity.ImageDestination;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DestinationServiceImp implements DestinationService {
    private final DestinationRepository destinationRepository;
    private final ImageDestinationRepository imageDestinationRepository;
    private final DestinationTypeRepository destinationTypeRepository;
    private final AddressRepository addressRepository;
    private final UploadFile uploadFile;
    private final DestinationMapper destinationMapper = Mappers.getMapper(DestinationMapper.class);

    public DestinationServiceImp(DestinationRepository destinationRepository, ImageDestinationRepository imageDestinationRepository, DestinationTypeRepository destinationTypeRepository, AddressRepository addressRepository, UploadFile uploadFile) {
        this.destinationRepository = destinationRepository;
        this.imageDestinationRepository = imageDestinationRepository;
        this.destinationTypeRepository = destinationTypeRepository;
        this.addressRepository = addressRepository;
        this.uploadFile = uploadFile;
    }

    @Override
    @Transactional
    public DestinationDataOutput getDestinationDetail(Long id) {
        Optional<Destination> destination = destinationRepository.findById(id);
        if (destination.isEmpty()){
            throw new NotFoundException(AppStr.Destination.tableDestination+AppStr.Base.whiteSpace+AppStr.Exception.notFound);
        }
        DestinationDataOutput output = destinationMapper.toDestinationDataOutput(destination.get());
        output.setDestinationType(destination.get().getDestinationType());
        output.setAddress(destination.get().getAddress());
        List<String> linkImageDestination = new ArrayList<>();
        List<ImageDestination> imageDestinations = imageDestinationRepository.findAllByDestination(destination.get());
        for (ImageDestination imageDestination :
             imageDestinations ) {
            linkImageDestination.add(imageDestination.getLink());
        }
        output.setImages(linkImageDestination);
        return output;
    }


    //Đang fixx
    @Override
    @Transactional
    public List<DestinationDataOutput> getListDestination(GetListDestinationInput input) {
        Optional<Address> address = addressRepository.findById(input.getIdAddress());
        Optional<DestinationType> destinationType = destinationTypeRepository.findById(input.getIdDestinationType());
        if (destinationType.isEmpty()){
            throw new NotFoundException(AppStr.DestinationType.destinationType+AppStr.Base.whiteSpace+AppStr.Exception.notFound);
        }
        List<Destination> destinations = destinationRepository.filter(address.get(),destinationType.get(), PageRequest.of(input.getPage(), input.getSize()));
        List<DestinationDataOutput> destinationDataOutputs = new ArrayList<>();
        for (Destination destination :
                destinations) {
            DestinationDataOutput destinationDataOutput = destinationMapper.toDestinationDataOutput(destination);
            destinationDataOutput.setDestinationType(destination.getDestinationType());
            destinationDataOutput.setAddress(destination.getAddress());
            List<ImageDestination> imageDestinations = imageDestinationRepository.findAllByDestination(destination);
            List<String> imageDestinationOutputs = new ArrayList<>();
            for (ImageDestination imageDestination :
                    imageDestinations ) {
                String imageDestinationOutput = imageDestination.getLink();
                imageDestinationOutputs.add(imageDestinationOutput);
            }
            destinationDataOutput.setImages(imageDestinationOutputs);
        }
        return destinationDataOutputs;
    }

    @Override
    @Transactional
    public DestinationDataOutput createDestination(DestinationDataInput input) {
        Destination destination = destinationMapper.toDestination(input,null);
        Optional<DestinationType> destinationType = destinationTypeRepository.findById(input.getIdDestinationType());
        if(destinationType.isEmpty()){
            throw new NotFoundException(AppStr.DestinationType.destinationType + AppStr.Base.whiteSpace+AppStr.Exception.notFound);
        }
        Optional<Address> address = addressRepository.findById(input.getIdAddress());
        if(address.isEmpty()){
            throw new NotFoundException(AppStr.Address.address + AppStr.Base.whiteSpace+AppStr.Exception.notFound);
        }
        destination.setDestinationType(destinationType.get());
        destination.setAddress(address.get());
        List<ImageDestination> imageDestinations = new ArrayList<>();
        List<String> links = uploadFile.getMultiUrl(input.getImages());
        for (String link:
             links) {
            ImageDestination imageDestination = new ImageDestination();
            imageDestination.setLink(link);
            imageDestination.setDestination(destination);
            imageDestinations.add(imageDestination);
        }
        destination.setImageDestinations(imageDestinations);
        destinationRepository.save(destination);
        imageDestinationRepository.saveAll(imageDestinations);
        DestinationDataOutput output = destinationMapper.toDestinationDataOutput(destination);
        output.setDestinationType(destination.getDestinationType());
        output.setAddress(destination.getAddress());
        List<String> linkImageDestination = new ArrayList<>();
        List<ImageDestination> imageDestinationList = imageDestinationRepository.findAllByDestination(destination);
        for (ImageDestination imageDestination :
                imageDestinationList ) {
            linkImageDestination.add(imageDestination.getLink());
        }
        output.setImages(linkImageDestination);
        return output;
    }

    @Override
    public DestinationDataInput editDestination(DestinationDataInput input, Long id) {
        return null;
    }

    @Override
    @Transactional
    public DestinationDataOutput deleteDestination(Long id) {
        Optional<Destination> destination = destinationRepository.findById(id);
        if (destination.isEmpty()){
            throw new NotFoundException(AppStr.Destination.tableDestination+AppStr.Base.whiteSpace+AppStr.Exception.notFound);
        }
        destinationRepository.delete(destination.get());
        DestinationDataOutput output = destinationMapper.toDestinationDataOutput(destination.get());
        output.setDestinationType(destination.get().getDestinationType());
        output.setAddress(destination.get().getAddress());
        List<String> linkImageDestination = new ArrayList<>();
        List<ImageDestination> imageDestinations = imageDestinationRepository.findAllByDestination(destination.get());
        for (ImageDestination imageDestination :
                imageDestinations ) {
            linkImageDestination.add(imageDestination.getLink());
        }
        output.setImages(linkImageDestination);
        return output;
    }
}
