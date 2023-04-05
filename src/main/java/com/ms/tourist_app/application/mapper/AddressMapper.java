package com.ms.tourist_app.application.mapper;

import com.ms.tourist_app.adapter.web.v1.transfer.parameter.addresses.AddressDataParameter;
import com.ms.tourist_app.application.input.addresses.AddressDataInput;
import com.ms.tourist_app.application.output.addresses.AddressDataOutput;
import com.ms.tourist_app.domain.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mappings({
            @Mapping(target = "detailAddress",source = "input.detailAddress"),
            @Mapping(target = "id",source = "id")
    })
    Address toAddress(AddressDataInput input,Long id);

    AddressDataInput createAddressInput(AddressDataParameter parameter);

    @Mappings({
            @Mapping(target = "id",source = "address.id"),
            @Mapping(target = "createBy",source = "address.createBy"),
            @Mapping(target = "createAt",source = "address.createAt"),
            @Mapping(target = "updateBy",source = "address.updateBy"),
            @Mapping(target = "updateAt",source = "address.updateAt"),
            @Mapping(target = "detailAddress",source = "address.detailAddress"),
            @Mapping(target = "longitude",source = "address.longitude"),
            @Mapping(target = "latitude",source = "address.latitude")
    })
    AddressDataOutput toAddressDataOutput(Address address);
}
