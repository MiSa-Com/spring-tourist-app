package com.ms.tourist_app.application.service.imp;

import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.application.dai.*;
import com.ms.tourist_app.application.input.hotels.GetListHotelDataInput;
import com.ms.tourist_app.application.input.hotels.HotelDataInput;
import com.ms.tourist_app.application.mapper.HotelMapper;
import com.ms.tourist_app.application.output.hotels.HotelDataOutput;
import com.ms.tourist_app.application.service.HotelService;
import com.ms.tourist_app.application.utils.JwtUtil;
import com.ms.tourist_app.application.utils.UploadFile;
import com.ms.tourist_app.config.exception.NotFoundException;
import com.ms.tourist_app.domain.entity.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImp implements HotelService {

    private final HotelRepository hotelRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ImageHotelRepository imageHotelRepository;
    private final UploadFile uploadFile;
    private final JwtUtil jwtUtil;
    private final HotelMapper hotelMapper = Mappers.getMapper(HotelMapper.class);
    private final ProvinceRepository provinceRepository;

    public HotelServiceImp(HotelRepository hotelRepository, AddressRepository addressRepository, UserRepository userRepository, ImageHotelRepository imageHotelRepository, UploadFile uploadFile, JwtUtil jwtUtil,
                           ProvinceRepository provinceRepository) {
        this.hotelRepository = hotelRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.imageHotelRepository = imageHotelRepository;
        this.uploadFile = uploadFile;
        this.jwtUtil = jwtUtil;
        this.provinceRepository = provinceRepository;
    }

    @Override
    @Transactional
    public HotelDataOutput createHotel(HotelDataInput hotelDataInput) {
        Optional<Address> address = addressRepository.findById(hotelDataInput.getIdAddress());
        if (address.isEmpty()) {
            throw new NotFoundException(AppStr.Address.address + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        Optional<User> user = userRepository.findById(hotelDataInput.getIdUser());
        if (user.isEmpty()) {
            throw new NotFoundException(AppStr.User.user + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        Hotel checkTelephone = hotelRepository.findAllByTelephone(hotelDataInput.getTelephone());
        if (checkTelephone != null) {
            throw new NotFoundException(AppStr.Hotel.telephone + AppStr.Base.whiteSpace + AppStr.Exception.duplicate);
        }
        Hotel hotel = hotelMapper.toHotel(hotelDataInput, null);
        hotel.setAddress(address.get());
        hotel.setUser(user.get());
        List<ImageHotel> imageHotels = new ArrayList<>();
        if (hotelDataInput.getImages().size()>1) {
            List<String> links = uploadFile.getMultiUrl(hotelDataInput.getImages());
            for (String link :
                    links) {
                ImageHotel imageHotel = new ImageHotel();
                imageHotel.setLink(link);
                imageHotel.setHotel(hotel);
                imageHotels.add(imageHotel);
            }
        }
        hotel.setImageHotels(imageHotels);
        hotel.setCreateBy(jwtUtil.getUserIdFromToken());
        hotelRepository.save(hotel);
        imageHotelRepository.saveAll(imageHotels);
        //Hotel data ouput
        HotelDataOutput hotelDataOutput = hotelMapper.toHotelDataOutput(hotel);
        hotelDataOutput.setCommentHotel(null);
        hotelDataOutput.setAddress(address.get());
        hotelDataOutput.setUser(user.get());
        hotelDataOutput.setImages(hotel.getImageHotels());
        return hotelDataOutput;
    }

    @Override
    public HotelDataOutput editHotel(Long id, HotelDataInput hotelDataInput) {
        Optional<Address> address = addressRepository.findById(hotelDataInput.getIdAddress());
        if (address.isEmpty()) {
            throw new NotFoundException(AppStr.Address.address + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        Optional<User> user = userRepository.findById(hotelDataInput.getIdUser());
        if (user.isEmpty()) {
            throw new NotFoundException(AppStr.User.user + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        Hotel checkTelephone = hotelRepository.findAllByTelephone(hotelDataInput.getTelephone());
        if (checkTelephone != null) {
            throw new NotFoundException(AppStr.Hotel.telephone + AppStr.Base.whiteSpace + AppStr.Exception.duplicate);
        }
        Hotel hotel = hotelMapper.toHotel(hotelDataInput, id);
        hotel.setAddress(address.get());
        hotel.setUser(user.get());
        List<ImageHotel> imageHotels = new ArrayList<>();
        if(hotelDataInput.getImages().size()>1){
            List<String> links = uploadFile.getMultiUrl(hotelDataInput.getImages());
            for (String link :
                    links) {
                ImageHotel imageHotel = new ImageHotel();
                imageHotel.setLink(link);
                imageHotel.setHotel(hotel);
                imageHotels.add(imageHotel);
            }
        }
        hotel.setUpdateBy(jwtUtil.getUserIdFromToken());
        hotel.setImageHotels(imageHotels);
        hotelRepository.save(hotel);
        imageHotelRepository.saveAll(imageHotels);
        //Hotel data ouput
        HotelDataOutput hotelDataOutput = hotelMapper.toHotelDataOutput(hotel);
        hotelDataOutput.setCommentHotel(null);
        hotelDataOutput.setAddress(address.get());
        hotelDataOutput.setUser(user.get());
        hotelDataOutput.setImages(hotel.getImageHotels());
        return hotelDataOutput;
    }

    @Override
    public List<HotelDataOutput> getListHotel(GetListHotelDataInput getListHotelDataInput) {
        List<Address> addresses;
        if (getListHotelDataInput.getIdProvince() == null) {
            addresses = addressRepository.searchWithoutProvince(getListHotelDataInput.getKeyword(), PageRequest.of(getListHotelDataInput.getPage(), getListHotelDataInput.getSize()));
        } else {
            Optional<Province> province = provinceRepository.findById(getListHotelDataInput.getIdProvince());
            if (province.isEmpty()) {
                throw new NotFoundException(AppStr.Province.tableProvince + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
            }
            addresses = addressRepository.search(getListHotelDataInput.getKeyword(), province.get(), PageRequest.of(getListHotelDataInput.getPage(), getListHotelDataInput.getSize()));
            if (addresses.isEmpty()) {
                throw new NotFoundException(AppStr.Address.address + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
            }
        }
        List<Hotel> hotels = new ArrayList<>();
        for (Address address :
                addresses) {
            hotels = hotelRepository.findAllByNameIsContainingIgnoreCaseAndAddress(getListHotelDataInput.getKeyword(), address);
        }
        List<HotelDataOutput> hotelDataOutputs = new ArrayList<>();
        for (Hotel hotel :
                hotels) {
            HotelDataOutput hotelDataOutput = hotelMapper.toHotelDataOutput(hotel);
            hotelDataOutput.setAddress(hotel.getAddress());
            hotelDataOutput.setUser(hotel.getUser());
            hotelDataOutput.setCommentHotel(hotel.getCommentHotels());
            hotelDataOutputs.add(hotelDataOutput);
        }
        return hotelDataOutputs;
    }

    @Override
    public HotelDataOutput viewHotelDetail(Long id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (hotel.isEmpty()) {
            throw new NotFoundException(AppStr.Hotel.tableHotel + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        HotelDataOutput hotelDataOutput = hotelMapper.toHotelDataOutput(hotel.get());
        hotelDataOutput.setAddress(hotel.get().getAddress());
        hotelDataOutput.setUser(hotel.get().getUser());
        hotelDataOutput.setCommentHotel(hotel.get().getCommentHotels());
        return hotelDataOutput;
    }

    @Override
    public HotelDataOutput deleteHotel(Long id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (hotel.isEmpty()) {
            throw new NotFoundException(AppStr.Hotel.tableHotel + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        hotelRepository.delete(hotel.get());
        HotelDataOutput hotelDataOutput = hotelMapper.toHotelDataOutput(hotel.get());
        hotelDataOutput.setAddress(hotel.get().getAddress());
        hotelDataOutput.setUser(hotel.get().getUser());
        hotelDataOutput.setCommentHotel(hotel.get().getCommentHotels());
        return hotelDataOutput;
    }
}
