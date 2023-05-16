package com.ms.tourist_app.application.service.imp;

import com.github.slugify.Slugify;
import com.google.maps.model.LatLng;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.application.dai.*;
import com.ms.tourist_app.application.input.destinations.*;
import com.ms.tourist_app.application.mapper.DestinationMapper;
import com.ms.tourist_app.application.output.destinations.CommentDestinationDataOutput;
import com.ms.tourist_app.application.output.destinations.DestinationDataOutput;
import com.ms.tourist_app.application.output.destinations.GetListDestinationCenterRadiusOutput;
import com.ms.tourist_app.application.service.DestinationService;
import com.ms.tourist_app.application.utils.Convert;
import com.ms.tourist_app.application.utils.GoogleMapApi;
import com.ms.tourist_app.application.utils.JwtUtil;
import com.ms.tourist_app.application.utils.UploadFile;
import com.ms.tourist_app.config.exception.NotFoundException;
import com.ms.tourist_app.domain.entity.*;
import com.ms.tourist_app.domain.entity.id.CommentDestinationId;
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
    private final Slugify slugify;
    private final UserRepository userRepository;
    private final CommentDestinationRepository commentDestinationRepository;


    public DestinationServiceImp(DestinationRepository destinationRepository, ImageDestinationRepository imageDestinationRepository, DestinationTypeRepository destinationTypeRepository, AddressRepository addressRepository, ProvinceRepository provinceRepository, UploadFile uploadFile, JwtUtil jwtUtil, Slugify slugify, UserRepository userRepository, CommentDestinationRepository commentDestinationRepository) {
        this.destinationRepository = destinationRepository;
        this.imageDestinationRepository = imageDestinationRepository;
        this.destinationTypeRepository = destinationTypeRepository;
        this.addressRepository = addressRepository;
        this.provinceRepository = provinceRepository;
        this.uploadFile = uploadFile;
        this.jwtUtil = jwtUtil;
        this.slugify = slugify;
        this.userRepository = userRepository;
        this.commentDestinationRepository = commentDestinationRepository;
    }

    @Override
    @Transactional
    public DestinationDataOutput getDestinationDetail(Long id) {
        Optional<Destination> destination = destinationRepository.findById(id);
        if (destination.isEmpty()) {
            throw new NotFoundException(AppStr.Destination.tableDestination + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        List<CommentDestination> commentDestinations = commentDestinationRepository.findAllByDestination(destination.get());
        DestinationDataOutput output = destinationMapper.toDestinationDataOutput(destination.get());
        output.setDestinationType(destination.get().getDestinationType());
        output.setAddress(destination.get().getAddress());
        List<String> linkImageDestination = new ArrayList<>();
        List<ImageDestination> imageDestinations = imageDestinationRepository.findAllByDestination(destination.get());
        for (ImageDestination imageDestination : imageDestinations) {
            linkImageDestination.add(imageDestination.getLink());
        }
        List<CommentDestinationDataOutput> commentDestinationDataOutputs = new ArrayList<>();
        for (CommentDestination commentDestination : commentDestinations) {
            CommentDestinationDataOutput dataOutput = new CommentDestinationDataOutput(commentDestination.getUser().getId(), commentDestination.getUser().getFirstName()+AppStr.Base.whiteSpace+commentDestination.getUser().getLastName(),commentDestination.getDestination().getId(), commentDestination.getContent(), commentDestination.getRating());
            commentDestinationDataOutputs.add(dataOutput);
        }
        output.setCommentDestinations(commentDestinationDataOutputs);
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
            List<Destination> destinations = destinationRepository.findByAddress(address, PageRequest.of(input.getPage(), input.getSize()));
            for (Destination destination : destinations) {
                List<CommentDestination> commentDestinations = commentDestinationRepository.findAllByDestination(destination);
                List<CommentDestinationDataOutput> commentDestinationDataOutputs = new ArrayList<>();
                for (CommentDestination commentDestination : commentDestinations) {
                    CommentDestinationDataOutput dataOutput = new CommentDestinationDataOutput(commentDestination.getUser().getId(), commentDestination.getUser().getFirstName()+commentDestination.getUser().getLastName(),commentDestination.getDestination().getId(), commentDestination.getContent(), commentDestination.getRating());
                    commentDestinationDataOutputs.add(dataOutput);
                }
                DestinationDataOutput destinationDataOutput = destinationMapper.toDestinationDataOutput(destination);
                destinationDataOutput.setDestinationType(destination.getDestinationType());
                destinationDataOutput.setAddress(destination.getAddress());
                List<ImageDestination> imageDestinations = imageDestinationRepository.findAllByDestination(destination);
                destinationDataOutput.setCommentDestinations(commentDestinationDataOutputs);
                List<String> imageDestinationOutputs = new ArrayList<>();
                for (ImageDestination imageDestination : imageDestinations) {
                    String imageDestinationOutput = imageDestination.getLink();
                    imageDestinationOutputs.add(imageDestinationOutput);
                }
                destinationDataOutput.setImages(imageDestinationOutputs);
                destinationDataOutputs.add(destinationDataOutput);
            }
        }
        return destinationDataOutputs;
    }

    @Override
    @Transactional
    public GetListDestinationCenterRadiusOutput getListDestinationCenterRadius(GetListDestinationCenterRadiusInput input) {
        List<Destination> allDestinations = destinationRepository.findAll();
        LatLng center = GoogleMapApi.getLatLng(input.getKeyword());
        List<Destination> destinationsInCircle = new ArrayList<>();
        for (Destination destination : allDestinations) {
            LatLng destinationLatLng = new LatLng(destination.getAddress().getLatitude(), destination.getAddress().getLongitude());
            if (GoogleMapApi.getFlightDistanceInKm(center, destinationLatLng) <= input.getRadius()) {
                destinationsInCircle.add(destination);
            }
        }
        List<Destination> searchDestinations = new ArrayList<>();
        List<Double> listDistance = new ArrayList<>();
        for (int i = 0; i < destinationsInCircle.size(); i++) {
            if (i >= input.getPage() * input.getSize() && i < (input.getPage()+1) * input.getSize()) {
                searchDestinations.add(destinationsInCircle.get(i));
                LatLng destinationLatLng = new LatLng(destinationsInCircle.get(i).getAddress().getLatitude(), destinationsInCircle.get(i).getAddress().getLongitude());
                 listDistance.add(GoogleMapApi.getFlightDistanceInKm(center, destinationLatLng));
            }
        }
        List<DestinationDataOutput> destinationDataOutputs = new ArrayList<>();

        for (Destination destination : searchDestinations) {
            DestinationDataOutput destinationDataOutput = destinationMapper.toDestinationDataOutput(destination);

            List<ImageDestination> imageDestinations = imageDestinationRepository.findAllByDestination(destination);
            List<String> imageDestinationOutputs = new ArrayList<>();
            for (ImageDestination imageDestination : imageDestinations) {
                String imageDestinationOutput = imageDestination.getLink();
                imageDestinationOutputs.add(imageDestinationOutput);
            }
            destinationDataOutput.setImages(imageDestinationOutputs);
            destinationDataOutputs.add(destinationDataOutput);
        }
        GetListDestinationCenterRadiusOutput output = new GetListDestinationCenterRadiusOutput(destinationDataOutputs, listDistance);
        return output;
    }

    @Override
    public List<DestinationDataOutput> getListDestinationByKeyword(GetListDestinationByKeywordInput input) {
        List<DestinationDataOutput> destinationDataOutputs = new ArrayList<>();
                List<Destination> destinations = destinationRepository.filter(input.getKeyword(), PageRequest.of(input.getPage(), input.getSize()));
                System.out.println(destinations.size());
                for (Destination destination : destinations) {
                    List<CommentDestination> commentDestinations = commentDestinationRepository.findAllByDestination(destination);
                    List<CommentDestinationDataOutput> commentDestinationDataOutputs = new ArrayList<>();
                    for (CommentDestination commentDestination : commentDestinations) {
                        CommentDestinationDataOutput dataOutput = new CommentDestinationDataOutput(commentDestination.getUser().getId(), commentDestination.getUser().getFirstName()+commentDestination.getUser().getLastName(),commentDestination.getDestination().getId(), commentDestination.getContent(), commentDestination.getRating());
                        commentDestinationDataOutputs.add(dataOutput);
                    }
                    DestinationDataOutput destinationDataOutput = destinationMapper.toDestinationDataOutput(destination);
                    destinationDataOutput.setDestinationType(destination.getDestinationType());
                    destinationDataOutput.setAddress(destination.getAddress());
                    List<ImageDestination> imageDestinations = imageDestinationRepository.findAllByDestination(destination);
                    List<String> imageDestinationOutputs = new ArrayList<>();
                    for (ImageDestination imageDestination : imageDestinations) {
                        String imageDestinationOutput = imageDestination.getLink();
                        imageDestinationOutputs.add(imageDestinationOutput);
                    }
                    destinationDataOutput.setCommentDestinations(commentDestinationDataOutputs);
                    destinationDataOutput.setImages(imageDestinationOutputs);
                    destinationDataOutputs.add(destinationDataOutput);
                }
        return destinationDataOutputs;
    }

    @Override
    public List<DestinationDataOutput> getListDestinationByCreateBy(Long id) {
        List<DestinationDataOutput> outputs = new ArrayList<>();
        List<Destination> destinations = destinationRepository.findAllByCreateBy(id);
        for (Destination destination: destinations){
            DestinationDataOutput output = destinationMapper.toDestinationDataOutput(destination);
            List<ImageDestination> imageDestinations = imageDestinationRepository.findAllByDestination(destination);
            List<String> imageDestinationOutputs = new ArrayList<>();
            for (ImageDestination imageDestination : imageDestinations) {
                String imageDestinationOutput = imageDestination.getLink();
                imageDestinationOutputs.add(imageDestinationOutput);
            }
            output.setImages(imageDestinationOutputs);
            outputs.add(output);
        }
        return outputs;
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
        destination.setSlug(slugify.slugify(input.getName()));
        destination.setSlugWithSpace(Convert.withSpace(slugify.slugify(input.getName())));
        destination.setSlugWithoutSpace(Convert.withoutSpace(slugify.slugify(input.getName())));
        if(jwtUtil.getUserIdFromToken()!=null){
            destination.setCreateBy(jwtUtil.getUserIdFromToken());
        }
        List<ImageDestination> imageDestinations = new ArrayList<>();
        if (input.getImages().size() > 1) {
            List<String> links = uploadFile.getMultiUrl(input.getImages());
            for (String link : links) {
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
        for (ImageDestination imageDestination : imageDestinationList) {
            linkImageDestination.add(imageDestination.getLink());
        }
        output.setImages(linkImageDestination);
        return output;
    }

    @Override
    public DestinationDataOutput editDestination(DestinationDataInput input, Long id) {
        Destination destination = destinationMapper.toDestination(input, id);
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
        destination.setSlug(slugify.slugify(input.getName()));
        destination.setSlugWithSpace(Convert.withSpace(slugify.slugify(input.getName())));
        destination.setSlugWithoutSpace(Convert.withoutSpace(slugify.slugify(input.getName())));
        if(jwtUtil.getUserIdFromToken()!=null){
            destination.setCreateBy(jwtUtil.getUserIdFromToken());
        }
        List<ImageDestination> imageDestinations = new ArrayList<>();
        if (input.getImages().size() > 1) {
            List<String> links = uploadFile.getMultiUrl(input.getImages());
            for (String link : links) {
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
        for (ImageDestination imageDestination : imageDestinationList) {
            linkImageDestination.add(imageDestination.getLink());
        }
        output.setImages(linkImageDestination);
        return output;
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
        for (ImageDestination imageDestination : imageDestinations) {
            linkImageDestination.add(imageDestination.getLink());
        }
        output.setImages(linkImageDestination);
        return output;
    }

    @Override
    public CommentDestinationDataOutput createComment(Long idDestination, CommentDestinationDataInput input) {
        Optional<Destination> destination = destinationRepository.findById(idDestination);
        if (destination.isEmpty()) {
            throw new NotFoundException(AppStr.Exception.notFound + AppStr.Base.whiteSpace + AppStr.Destination.tableDestination);
        }
        Long idUser = jwtUtil.getUserIdFromToken();
        if (idUser == null) {
            throw new NotFoundException(AppStr.Forbiden.notSignIn);
        }
        Optional<User> user = userRepository.findById(idUser);

        CommentDestinationId commentDestinationId = new CommentDestinationId(idUser, idDestination);
        CommentDestination commentDestination = new CommentDestination(commentDestinationId, user.get(), destination.get(), input.getContent(), input.getRating());
        commentDestinationRepository.save(commentDestination);
        List<CommentDestination> commentDestinations = commentDestinationRepository.findAllByDestination(destination.get());
        commentDestinations.add(commentDestination);
        user.get().setCommentDestinations(commentDestinations);
        return new CommentDestinationDataOutput(idUser,commentDestination.getUser().getFirstName()+commentDestination.getUser().getLastName(), idDestination, input.getContent(), input.getRating());
    }

    @Override
    public CommentDestinationDataOutput editComment(CommentDestinationId commentDestinationId, CommentDestinationDataInput input) {
        Optional<CommentDestination> commentDestination = commentDestinationRepository.findById(commentDestinationId);
        Optional<User> user = userRepository.findById(commentDestinationId.getIdUser());
        Optional<Destination> destination = destinationRepository.findById(commentDestinationId.getIdUser());
        commentDestination.get().setContent(input.getContent());
        commentDestination.get().setRating(input.getRating());
        commentDestinationRepository.save(commentDestination.get());
        List<CommentDestination> commentDestinations = commentDestinationRepository.findAllByDestination(destination.get());
        commentDestinations.add(commentDestination.get());
        user.get().setCommentDestinations(commentDestinations);
        return new CommentDestinationDataOutput(commentDestinationId.getIdUser(), commentDestination.get().getUser().getFirstName()+commentDestination.get().getUser().getLastName(),commentDestinationId.getIdDestination(), input.getContent(), input.getRating());
    }

    @Override
    public List<DestinationDataOutput> selectTopCreateAt(SelectTopCreateAtInput input) {
        List<DestinationDataOutput> destinationDataOutputs = new ArrayList<>();
        List<Destination> destinations = destinationRepository.selectTopCreateAt(PageRequest.of(input.getPage(), input.getSize()));
        for (Destination destination : destinations) {
            List<CommentDestination> commentDestinations = commentDestinationRepository.findAllByDestination(destination);
            List<CommentDestinationDataOutput> commentDestinationDataOutputs = new ArrayList<>();
            for (CommentDestination commentDestination : commentDestinations) {
                CommentDestinationDataOutput dataOutput = new CommentDestinationDataOutput(commentDestination.getUser().getId(),commentDestination.getUser().getFirstName()+commentDestination.getUser().getLastName(), commentDestination.getDestination().getId(), commentDestination.getContent(), commentDestination.getRating());
                commentDestinationDataOutputs.add(dataOutput);
            }
            DestinationDataOutput destinationDataOutput = destinationMapper.toDestinationDataOutput(destination);
            destinationDataOutput.setDestinationType(destination.getDestinationType());
            destinationDataOutput.setAddress(destination.getAddress());
            List<ImageDestination> imageDestinations = imageDestinationRepository.findAllByDestination(destination);
            List<String> imageDestinationOutputs = new ArrayList<>();
            for (ImageDestination imageDestination : imageDestinations) {
                String imageDestinationOutput = imageDestination.getLink();
                imageDestinationOutputs.add(imageDestinationOutput);
            }
            destinationDataOutput.setCommentDestinations(commentDestinationDataOutputs);
            destinationDataOutput.setImages(imageDestinationOutputs);
            destinationDataOutputs.add(destinationDataOutput);
        }
        return destinationDataOutputs;
    }


}
