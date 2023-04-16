package com.ms.tourist_app.application.service.imp;

import com.google.maps.model.LatLng;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.application.dai.*;
import com.ms.tourist_app.application.input.destinations.DestinationDataInput;
import com.ms.tourist_app.application.input.destinations.GetListDestinationByKeywordInput;
import com.ms.tourist_app.application.input.destinations.GetListDestinationCenterRadiusInput;
import com.ms.tourist_app.application.input.destinations.GetListDestinationByProvinceInput;
import com.ms.tourist_app.application.mapper.DestinationMapper;
import com.ms.tourist_app.application.output.destinations.DestinationDataOutput;
import com.ms.tourist_app.application.service.DestinationService;
import com.ms.tourist_app.application.utils.GoogleMapApi;
import com.ms.tourist_app.application.utils.JwtUtil;
import com.ms.tourist_app.application.utils.UploadFile;
import com.ms.tourist_app.config.exception.NotFoundException;
import com.ms.tourist_app.domain.entity.*;
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
    private final ProvinceRepository provinceRepository;
    private final UploadFile uploadFile;
    private final DestinationMapper destinationMapper = Mappers.getMapper(DestinationMapper.class);
    private final JwtUtil jwtUtil;

    public DestinationServiceImp(DestinationRepository destinationRepository, ImageDestinationRepository imageDestinationRepository, DestinationTypeRepository destinationTypeRepository, AddressRepository addressRepository, ProvinceRepository provinceRepository, UploadFile uploadFile, JwtUtil jwtUtil) {
        this.destinationRepository = destinationRepository;
        this.imageDestinationRepository = imageDestinationRepository;
        this.destinationTypeRepository = destinationTypeRepository;
        this.addressRepository = addressRepository;
        this.provinceRepository = provinceRepository;
        this.uploadFile = uploadFile;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @Transactional
    public DestinationDataOutput getDestinationDetail(Long id) {
        Optional<Destination> destination = destinationRepository.findById(id);
        if (destination.isEmpty()) {
            throw new NotFoundException(AppStr.Destination.tableDestination + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        DestinationDataOutput output = destinationMapper.toDestinationDataOutput(destination.get());
        output.setDestinationType(destination.get().getDestinationType());
        output.setAddress(destination.get().getAddress());
        List<String> linkImageDestination = new ArrayList<>();
        List<ImageDestination> imageDestinations = imageDestinationRepository.findAllByDestination(destination.get());
        for (ImageDestination imageDestination :
                imageDestinations) {
            linkImageDestination.add(imageDestination.getLink());
        }
        output.setImages(linkImageDestination);
        return output;
    }


    @Override
    @Transactional
    public List<DestinationDataOutput> getListDestinationByProvince(GetListDestinationByProvinceInput input) {
        Optional<Province> province = provinceRepository.findById(input.getIdProvince());
        if (province.isEmpty()) {
            throw new NotFoundException(AppStr.Province.tableProvince + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        List<Address> addresses = addressRepository.findAllByProvince(province.get());
        List<DestinationDataOutput> destinationDataOutputs = new ArrayList<>();
        for (Address address : addresses) {
            List<Destination> destinations = destinationRepository.findByProvince(address, PageRequest.of(input.getPage(), input.getSize()));
            for (Destination destination :
                    destinations) {
                DestinationDataOutput destinationDataOutput = destinationMapper.toDestinationDataOutput(destination);
                destinationDataOutput.setDestinationType(destination.getDestinationType());
                destinationDataOutput.setAddress(destination.getAddress());
                List<ImageDestination> imageDestinations = imageDestinationRepository.findAllByDestination(destination);
                List<String> imageDestinationOutputs = new ArrayList<>();
                for (ImageDestination imageDestination :
                        imageDestinations) {
                    String imageDestinationOutput = imageDestination.getLink();
                    imageDestinationOutputs.add(imageDestinationOutput);
                }
                destinationDataOutput.setImages(imageDestinationOutputs);
            }
        }
        return destinationDataOutputs;
    }

    @Override
    @Transactional
    public List<DestinationDataOutput> getListDestinationCenterRadius(GetListDestinationCenterRadiusInput input) {
        List<Destination> allDestinations = destinationRepository.findAllDestinations();
        List<Destination> searchDestinations = new ArrayList<>();
        LatLng center = GoogleMapApi.getLatLng(input.getKeyword());
        for (int i = 0; i < allDestinations.size(); i++) {
            if (i < input.getPage() * input.getSize()) {
                continue;
            }
            if (i >= (input.getPage() + 1) * input.getSize()) {
                break;
            }
            LatLng latLngDest = new LatLng(allDestinations.get(i).getAddress().getLatitude(), allDestinations.get(i).getAddress().getLongitude());
            double distance = GoogleMapApi.getFlightDistanceInKm(center, latLngDest);
            if (distance <= input.getRadius()) {
                searchDestinations.add(allDestinations.get(i));
            }
        }
        List<DestinationDataOutput> destinationDataOutputs = new ArrayList<>();

        for (Destination destination : searchDestinations) {
            DestinationDataOutput destinationDataOutput = destinationMapper.toDestinationDataOutput(destination);
            destinationDataOutputs.add(destinationDataOutput);
        }
        return destinationDataOutputs;
    }

    @Override
    public List<DestinationDataOutput> getListDestinationByKeyword(GetListDestinationByKeywordInput input) {
        List<DestinationDataOutput> destinationDataOutputs = new ArrayList<>();
                List<Province> provinces = provinceRepository.findAllByNameContainingIgnoreCase(input.getKeyword(),PageRequest.of(input.getPage(),input.getSize()));
        for (Province province :
                provinces) {
            List<Address> addresses = addressRepository.findAllByProvinceOrOtherOrDetailAddress(province,input.getKeyword());
            for (Address address :
                    addresses) {
                List<Destination> destinations = destinationRepository.filter(address,input.getKeyword(),PageRequest.of(input.getPage(),input.getSize()));
                for (Destination destination :
                        destinations) {
                    DestinationDataOutput destinationDataOutput = destinationMapper.toDestinationDataOutput(destination);
                    destinationDataOutput.setDestinationType(destination.getDestinationType());
                    destinationDataOutput.setAddress(destination.getAddress());
                    List<ImageDestination> imageDestinations = imageDestinationRepository.findAllByDestination(destination);
                    List<String> imageDestinationOutputs = new ArrayList<>();
                    for (ImageDestination imageDestination :
                            imageDestinations) {
                        String imageDestinationOutput = imageDestination.getLink();
                        imageDestinationOutputs.add(imageDestinationOutput);
                    }
                    destinationDataOutput.setImages(imageDestinationOutputs);
                }
            }
        }
        return destinationDataOutputs;
    }

    @Override
    @Transactional
    public DestinationDataOutput createDestination(DestinationDataInput input) {
        Destination destination = destinationMapper.toDestination(input, null);
        Optional<DestinationType> destinationType = destinationTypeRepository.findById(input.getIdDestinationType());
        if (destinationType.isEmpty()) {
            throw new NotFoundException(AppStr.DestinationType.destinationType + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        Optional<Address> address = addressRepository.findById(input.getIdAddress());
        if (address.isEmpty()) {
            throw new NotFoundException(AppStr.Address.address + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        destination.setDestinationType(destinationType.get());
        destination.setAddress(address.get());
        destination.setCreateBy(jwtUtil.getUserIdFromToken());
        List<ImageDestination> imageDestinations = new ArrayList<>();
        if (input.getImages().size() > 1) {
            List<String> links = uploadFile.getMultiUrl(input.getImages());
            for (String link :
                    links) {
                ImageDestination imageDestination = new ImageDestination();
                imageDestination.setLink(link);
                imageDestination.setDestination(destination);
                imageDestinations.add(imageDestination);
            }
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
                imageDestinationList) {
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
        if (destination.isEmpty()) {
            throw new NotFoundException(AppStr.Destination.tableDestination + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        destinationRepository.delete(destination.get());
        DestinationDataOutput output = destinationMapper.toDestinationDataOutput(destination.get());
        output.setDestinationType(destination.get().getDestinationType());
        output.setAddress(destination.get().getAddress());
        List<String> linkImageDestination = new ArrayList<>();
        List<ImageDestination> imageDestinations = imageDestinationRepository.findAllByDestination(destination.get());
        for (ImageDestination imageDestination :
                imageDestinations) {
            linkImageDestination.add(imageDestination.getLink());
        }
        output.setImages(linkImageDestination);
        return output;
    }
}
