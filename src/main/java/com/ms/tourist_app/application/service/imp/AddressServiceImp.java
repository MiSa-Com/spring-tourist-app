package com.ms.tourist_app.application.service.imp;

import com.github.slugify.Slugify;
import com.google.maps.model.LatLng;
import com.ms.tourist_app.application.constants.AppConst;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.application.dai.AddressRepository;
import com.ms.tourist_app.application.dai.DestinationRepository;
import com.ms.tourist_app.application.dai.ProvinceRepository;
import com.ms.tourist_app.application.dai.UserRepository;
import com.ms.tourist_app.application.input.addresses.AddressDataInput;
import com.ms.tourist_app.application.input.addresses.GetListAddressInput;
import com.ms.tourist_app.application.input.users.UserDataInput;
import com.ms.tourist_app.application.mapper.AddressMapper;
import com.ms.tourist_app.application.mapper.ProvinceMapper;
import com.ms.tourist_app.application.output.addresses.AddressDataOutput;
import com.ms.tourist_app.application.output.provinces.ProvinceDataOutput;
import com.ms.tourist_app.application.service.AddressService;
import com.ms.tourist_app.application.service.UserService;
import com.ms.tourist_app.application.utils.Convert;
import com.ms.tourist_app.application.utils.GoogleMapApi;
import com.ms.tourist_app.application.utils.JwtUtil;
import com.ms.tourist_app.config.exception.BadRequestException;
import com.ms.tourist_app.config.exception.NotFoundException;
import com.ms.tourist_app.domain.entity.Address;
import com.ms.tourist_app.domain.entity.Destination;
import com.ms.tourist_app.domain.entity.Province;
import com.ms.tourist_app.domain.entity.User;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AddressServiceImp implements AddressService {
    private final Slugify slugify;

    private final AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);
    private final ProvinceMapper provinceMapper = Mappers.getMapper(ProvinceMapper.class);
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final DestinationRepository destinationRepository;
    private final ProvinceRepository provinceRepository;

    public AddressServiceImp(Slugify slugify, AddressRepository addressRepository, UserRepository userRepository, UserService userService, JwtUtil jwtUtil,
                             DestinationRepository destinationRepository,
                             ProvinceRepository provinceRepository) {
        this.slugify = slugify.withTransliterator(true);
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.destinationRepository = destinationRepository;
        this.provinceRepository = provinceRepository;
    }

    private boolean checkCoordinate(Double longitude, Double latitude) {
        Address address = addressRepository.findByLongitudeAndLatitude(longitude, latitude);
        return address != null;
    }


    @Override
    @Transactional
    public AddressDataOutput createAddress(AddressDataInput input) {
        Optional<Province> province = provinceRepository.findById(input.getIdProvince());
        if (province.isEmpty()) {
            throw new NotFoundException(AppStr.Province.tableProvince + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        LatLng latLng = GoogleMapApi.getLatLng(input.getDetailAddress());
        if (this.checkCoordinate(latLng.lng, latLng.lat)) {
            throw new BadRequestException(AppStr.Address.address + AppStr.Base.whiteSpace + AppStr.Exception.duplicate);
        }
        Address address = addressMapper.toAddress(input, null);
        address.setLongitude(latLng.lng);
        address.setLatitude(latLng.lat);
        address.setProvince(province.get());
        address.setCreateBy(jwtUtil.getUserIdFromToken());
        address.setSlug(slugify.slugify(input.getDetailAddress()));
        address.setSlugWithSpace(Convert.withSpace(slugify.slugify(input.getDetailAddress())));
        address.setSlugWithoutSpace(Convert.withoutSpace(slugify.slugify(input.getDetailAddress())));
        addressRepository.save(address);
        ProvinceDataOutput provinceDataOutput = provinceMapper.toProvinceDataOutput(province.get());
        // convert tu address sang output
        return new AddressDataOutput(address.getId(), address.getCreateBy(),
                address.getCreateAt(), address.getUpdateBy(),
                address.getUpdateAt(), address.getSlug(), address.getLongitude(),
                address.getLatitude(), provinceDataOutput,
                address.getDetailAddress());
    }

    @Override
    @Transactional
    public AddressDataOutput viewDataAddress(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isEmpty()) {
            throw new NotFoundException(AppStr.Address.address + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        return addressMapper.toAddressDataOutput(address.get());
    }

    @Override
    @Transactional
    public AddressDataOutput editAddress(Long id, AddressDataInput input) {
        Optional<Province> province = provinceRepository.findById(input.getIdProvince());
        if (province.isEmpty()) {
            throw new NotFoundException(AppStr.Province.tableProvince + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        Optional<Address> oldAddress = addressRepository.findById(id);
        if (oldAddress.isEmpty()) {
            throw new NotFoundException(AppStr.Address.address + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        Address address = addressMapper.toAddress(input, id);
        address.setProvince(province.get());
        address.setUpdateBy(jwtUtil.getUserIdFromToken());
        address.setSlugWithSpace(Convert.withSpace(slugify.slugify(input.getDetailAddress())));
        address.setSlugWithoutSpace(Convert.withoutSpace(slugify.slugify(input.getDetailAddress())));
        addressRepository.save(address);
        return addressMapper.toAddressDataOutput(address);
    }

    @Override
    @Transactional
    public List<AddressDataOutput> getListAddressDataOutput(GetListAddressInput input) {
        List<Address> addresses;
        if (input.getIdProvince() == null) {
            addresses = addressRepository.searchWithoutProvince(input.getKeyword().trim(), PageRequest.of(input.getPage(), input.getSize()));
        } else {
            Optional<Province> province = provinceRepository.findById(input.getIdProvince());
            if (province.isEmpty()) {
                throw new NotFoundException(AppStr.Province.tableProvince + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
            }
            addresses = addressRepository.search(input.getKeyword().trim(), province.get(), PageRequest.of(input.getPage(), input.getSize()));
        }

        List<Address> savedAddress = new ArrayList<>();
        if (addresses.isEmpty()) {
            List<Address> resultsToAdd = GoogleMapApi.findAddressFromText(input.getKeyword(), AppConst.MapApi.defaultNbResult);
            // search text google map
            addresses.addAll(resultsToAdd);

            // charge into database
            for (Address address : addresses) {
                if (!checkCoordinate(address.getLongitude(), address.getLatitude())) {
                    String detailAddress = address.getDetailAddress();
                    slugify.withTransliterator(true);
                    String formattedAddress = Convert.withoutSpace(slugify.slugify(detailAddress));
                    List<Province> listProvince = provinceRepository.findProvinceByNameContaining(formattedAddress);
                    if (listProvince.size() > 0) {
                        address.setProvince(listProvince.get(0));
                    }
                    Address savedAd = addressRepository.save(address);
                    savedAddress.add(savedAd);
                }
            }
            addresses.clear();
            addresses.addAll(savedAddress);
        }
        List<AddressDataOutput> addressDataOutputs = new ArrayList<>();
        for (Address address : addresses) {
            AddressDataOutput addressDataOutput = addressMapper.toAddressDataOutput(address);

            addressDataOutputs.add(addressDataOutput);
        }
        return addressDataOutputs;
    }

    @Override
    @Transactional
    public AddressDataOutput deleteAddress(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isEmpty()) {
            throw new NotFoundException(AppStr.Address.address + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        //Sửa user khi delete address
        List<User> users = userRepository.findAllByAddress(address.get());
        for (User u :
                users) {
            UserDataInput userDataInput = new UserDataInput(u.getFirstName(), u.getLastName(), u.getDateOfBirth().toString(), null, u.getTelephone(), u.getEmail(), u.getPassword());
            userService.editUser(u.getId(), userDataInput);
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
