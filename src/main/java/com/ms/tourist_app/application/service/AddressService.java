package com.ms.tourist_app.application.service;

import com.ms.tourist_app.application.input.addresses.AddressDataInput;
import com.ms.tourist_app.application.input.addresses.GetListAddressInput;
import com.ms.tourist_app.application.output.addresses.AddressDataOutput;

import java.util.List;

public interface AddressService {
    AddressDataOutput createAddress(AddressDataInput input);
    AddressDataOutput viewDataAddress(Long id);
    AddressDataOutput editAddress(Long id, AddressDataInput input);
    List<AddressDataOutput> getListAddressDataOutput(GetListAddressInput input);
    AddressDataOutput deleteAddress(Long id);
}
