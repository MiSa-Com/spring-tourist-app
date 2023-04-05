package com.ms.tourist_app.application.service.imp;

import com.github.slugify.Slugify;
import com.google.maps.model.LatLng;
import com.ms.tourist_app.application.constants.AppEnv;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.application.dai.AddressRepository;
import com.ms.tourist_app.application.dai.DestinationRepository;
import com.ms.tourist_app.application.dai.UserRepository;
import com.ms.tourist_app.application.input.addresses.AddressDataInput;
import com.ms.tourist_app.application.input.addresses.GetListAddressInput;
import com.ms.tourist_app.application.input.users.UserDataInput;
import com.ms.tourist_app.application.mapper.AddressMapper;
import com.ms.tourist_app.application.output.addresses.AddressDataOutput;
import com.ms.tourist_app.application.service.AddressService;
import com.ms.tourist_app.application.service.UserService;
import com.ms.tourist_app.application.utils.GoogleMapApi;
import com.ms.tourist_app.application.utils.JwtUtil;
import com.ms.tourist_app.config.exception.NotFoundException;
import com.ms.tourist_app.domain.entity.Address;
import com.ms.tourist_app.domain.entity.Destination;
import com.ms.tourist_app.domain.entity.User;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImp implements AddressService {
    private Slugify slugify;

    private final AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final DestinationRepository destinationRepository;

    public AddressServiceImp(Slugify slugify, AddressRepository addressRepository, UserRepository userRepository, UserService userService, JwtUtil jwtUtil,
                             DestinationRepository destinationRepository) {
        this.slugify = slugify;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.destinationRepository = destinationRepository;
    }
    private boolean checkCoordinate(Double longitude,Double latitude){
        Address address = addressRepository.findByLongitudeAndLatitude(longitude,latitude);
        return address != null;
    }


    @Override
    @Transactional
    public AddressDataOutput createAddress(AddressDataInput input) {
        LatLng latLng = GoogleMapApi.getLatLng(input.getDetailAddress() + input.getProvince());
        if (latLng == null) {
            throw new NotFoundException(AppStr.Address.address+AppStr.Base.whiteSpace+AppStr.Exception.notFound);
        }
        if (this.checkCoordinate(latLng.lng,latLng.lat)){
            throw new NotFoundException(AppStr.Address.address+AppStr.Base.whiteSpace+AppStr.Exception.duplicate);
        }
        Address address = addressMapper.toAddress(input,null);
        address.setLongitude(latLng.lng);
        address.setLatitude(latLng.lat);
        Long idCreate = jwtUtil.getUserIdFromToken();
        address.setCreateBy(idCreate);
        slugify = slugify.withTransliterator(true);
        address.setOther(slugify.slugify(input.getDetailAddress() +AppStr.Base.dash +input.getProvince()));
        addressRepository.save(address);

        // convert tuwf address sang output
        AddressDataOutput addressDataOutput = new AddressDataOutput(address.getId(),address.getCreateBy(),
                                                                    address.getCreateAt(),address.getUpdateBy(),
                                                                    address.getUpdateAt(),address.getOther(),address.getLongitude(),
                                                                    address.getLatitude(), address.getProvince(),
                                                                    address.getDetailAddress());
        return addressDataOutput;
    }

    @Override
    @Transactional
    public AddressDataOutput viewDataAddress(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isEmpty()){
            throw new NotFoundException(AppStr.Address.address+AppStr.Base.whiteSpace+AppStr.Exception.notFound);
        }
        AddressDataOutput addressDataOutput = addressMapper.toAddressDataOutput(address.get());
        return addressDataOutput;
    }

    @Override
    @Transactional
    public AddressDataOutput editAddress(Long id, AddressDataInput input) {
        Optional<Address> oldAddress = addressRepository.findById(id);
        if (oldAddress.isEmpty()){
            throw new NotFoundException(AppStr.Address.address+AppStr.Base.whiteSpace+AppStr.Exception.notFound);
        }
        Address address = addressMapper.toAddress(input, id);
        address.setUpdateBy(jwtUtil.getUserIdFromToken());
        addressRepository.save(address);
        AddressDataOutput addressDataOutput = addressMapper.toAddressDataOutput(address);
        return addressDataOutput;
    }

    @Override
    @Transactional
    public List<AddressDataOutput> getListAddressDataOutput(GetListAddressInput input) {

        List<Address> addresses = addressRepository.search(input.getKeyword().trim(), PageRequest.of(input.getPage(), input.getSize()));

        List<AddressDataOutput> addressDataOutputs = new ArrayList<>();
        /**
         * Neu address empty thi tim goi y tren Google Map
         */

        for (Address address :
                addresses) {
            AddressDataOutput addressDataOutput = addressMapper.toAddressDataOutput(address);
            addressDataOutputs.add(addressDataOutput);
        }
        return addressDataOutputs;
    }

    @Override
    @Transactional
    public AddressDataOutput deleteAddress(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isEmpty()){
            throw new NotFoundException(AppStr.Address.address+AppStr.Base.whiteSpace+AppStr.Exception.notFound);
        }
        //Sửa user khi delete address
        List<User> users = userRepository.findAllByAddress(address.get());
        for (User u :
                users) {
            UserDataInput userDataInput = new UserDataInput(u.getFirstName(),u.getLastName(),u.getDateOfBirth().toString(),null,u.getTelephone(),u.getEmail(),u.getPassword());
            userService.editUser(u.getId(),userDataInput);
        }
        List<Destination> destinations = destinationRepository.findAllByAddress(address.get());
        for (Destination d :
                destinations) {
            d.setAddress(null);
        }
        //=======
        addressRepository.delete(address.get());
        AddressDataOutput addressDataOutput = addressMapper.toAddressDataOutput(address.get());
        return addressDataOutput;
    }
}
