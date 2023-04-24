package com.ms.tourist_app.application.service.imp;

import com.github.slugify.Slugify;
import com.google.maps.model.LatLng;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.application.dai.AddressRepository;
import com.ms.tourist_app.application.dai.ProvinceRepository;
import com.ms.tourist_app.application.input.provinces.GetListProvinceDataInput;
import com.ms.tourist_app.application.input.provinces.ProvinceDataInput;
import com.ms.tourist_app.application.mapper.ProvinceMapper;
import com.ms.tourist_app.application.output.provinces.ProvinceDataOutput;
import com.ms.tourist_app.application.service.ProvinceService;
import com.ms.tourist_app.application.utils.Convert;
import com.ms.tourist_app.application.utils.GoogleMapApi;
import com.ms.tourist_app.application.utils.JwtUtil;
import com.ms.tourist_app.config.exception.BadRequestException;
import com.ms.tourist_app.config.exception.NotFoundException;
import com.ms.tourist_app.domain.entity.Address;
import com.ms.tourist_app.domain.entity.Province;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProvinceServiceImp implements ProvinceService {
    private final Slugify slugify;

    private final ProvinceMapper provinceMapper = Mappers.getMapper(ProvinceMapper.class);
    private final ProvinceRepository provinceRepository;
    private final AddressRepository addressRepository;
    private final JwtUtil jwtUtil;

    public ProvinceServiceImp(Slugify slugify, ProvinceRepository provinceRepository, AddressRepository addressRepository, JwtUtil jwtUtil) {
        this.slugify = slugify.withTransliterator(true);
        this.provinceRepository = provinceRepository;
        this.addressRepository = addressRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public ProvinceDataOutput createProvince(ProvinceDataInput provinceDataInput) {
        Province provinceCheck = provinceRepository.findByNameContainingIgnoreCase(provinceDataInput.getName());
        if (provinceCheck != null) {
            throw new BadRequestException(AppStr.Province.tableProvince + AppStr.Base.whiteSpace + AppStr.Exception.duplicate);
        }
        Province province = provinceMapper.toProvince(provinceDataInput, null);
        province.setSlug(slugify.slugify(provinceDataInput.getName()));
        province.setSlugWithSpace(Convert.withSpace(slugify.slugify(provinceDataInput.getName())));
        province.setSlugWithoutSpace(Convert.withoutSpace(slugify.slugify(provinceDataInput.getName())));
        if (jwtUtil.getUserIdFromToken() != null) {
            province.setCreateBy(jwtUtil.getUserIdFromToken());
        }
        provinceRepository.save(province);
        return provinceMapper.toProvinceDataOutput(province);
    }

    @Override
    public List<ProvinceDataOutput> getListProvinceDataOutput(GetListProvinceDataInput getListProvinceDataInput) {
        List<ProvinceDataOutput> provinceDataOutputs = new ArrayList<>();
        List<Province> provinces = provinceRepository.findAllByNameContainingIgnoreCase(getListProvinceDataInput.getKeyword(), PageRequest.of(getListProvinceDataInput.getPage(), getListProvinceDataInput.getSize()));
        System.out.println(provinces.size());
        for (Province province : provinces) {
            ProvinceDataOutput provinceDataOutput = provinceMapper.toProvinceDataOutput(province);
            provinceDataOutputs.add(provinceDataOutput);
        }
        return provinceDataOutputs;
    }

    @Override
    public ProvinceDataOutput editProvince(Long id, ProvinceDataInput provinceDataInput) {
        Optional<Province> provinceCheck = provinceRepository.findById(id);
        if (provinceCheck.isEmpty()) {
            throw new BadRequestException(AppStr.Province.tableProvince + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        Province province = provinceMapper.toProvince(provinceDataInput, id);
        provinceRepository.save(province);
        province.setUpdateBy(jwtUtil.getUserIdFromToken());
        province.setSlugWithSpace(Convert.withSpace(slugify.slugify(provinceDataInput.getName())));
        province.setSlugWithoutSpace(Convert.withoutSpace(slugify.slugify(provinceDataInput.getName())));
        return provinceMapper.toProvinceDataOutput(province);
    }

    @Override
    public ProvinceDataOutput deleteProvince(Long id) {
        Optional<Province> province = provinceRepository.findById(id);
        if (province.isEmpty()) {
            throw new BadRequestException(AppStr.Province.tableProvince + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        List<Address> addresses = addressRepository.findAllByProvince(province.get());
        for (Address address : addresses) {
            address.setProvince(null);
            address.setId(address.getId());
            addressRepository.save(address);
        }
        provinceRepository.delete(province.get());
        return provinceMapper.toProvinceDataOutput(province.get());
    }

    @Override
    public Long getProvinceByCoordinate(Double lon, Double lat) {
        LatLng latLng = new LatLng(lat, lon);
        String formattedAddress = GoogleMapApi.getFormattedAddressFromLngLat(latLng);
        slugify.withTransliterator(true);
        formattedAddress = Convert.withoutSpace(slugify.slugify(formattedAddress));
        List<Province> listProvince = provinceRepository.findProvinceByNameContaining(formattedAddress);
        if (listProvince.size() > 0) {
            return listProvince.get(0).getId();
        }
        throw new NotFoundException(AppStr.Province.tableProvince + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
    }


}
