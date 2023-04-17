package com.ms.tourist_app.adapter.web.v1.controller;

import com.ms.tourist_app.adapter.web.base.ResponseUtil;
import com.ms.tourist_app.adapter.web.base.RestApiV1;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.addresses.AddressDataParameter;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.addresses.GetListAddressParameter;
import com.ms.tourist_app.application.constants.UrlConst;
import com.ms.tourist_app.application.input.addresses.AddressDataInput;
import com.ms.tourist_app.application.input.addresses.GetListAddressInput;
import com.ms.tourist_app.application.mapper.AddressMapper;
import com.ms.tourist_app.application.output.addresses.AddressDataOutput;
import com.ms.tourist_app.application.service.imp.AddressServiceImp;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestApiV1
public class AddressController {

    private final AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);

    private final AddressServiceImp addressService;

    public AddressController(AddressServiceImp addressServiceImp) {
        this.addressService = addressServiceImp;
    }


    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping(UrlConst.Address.address)
    public ResponseEntity<?> createAddress(@Valid @RequestBody AddressDataParameter parameter){
        AddressDataInput addressDataInput = addressMapper.createAddressInput(parameter);
        AddressDataOutput addressDataOutput = addressService.createAddress(addressDataInput);
        return ResponseUtil.restSuccess(addressDataOutput);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(UrlConst.Address.address)
    public ResponseEntity<?> getListAddress(@Valid GetListAddressParameter parameter){
        GetListAddressInput getListAddressInput = new GetListAddressInput(parameter.getPage(), parameter.getSize(), parameter.getIdProvince(), parameter.getKeyword());
        List<AddressDataOutput> addressDataOutput = addressService.getListAddressDataOutput(getListAddressInput);
        return ResponseUtil.restSuccess(addressDataOutput);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping(UrlConst.Address.getAddressId)
    public ResponseEntity<?> editAddress(@PathVariable(UrlConst.id)Long id, @Valid @RequestBody AddressDataParameter parameter){
        AddressDataInput addressDataInput = addressMapper.createAddressInput(parameter);
        AddressDataOutput addressDataOutput = addressService.editAddress(id,addressDataInput);
        return ResponseUtil.restSuccess(addressDataOutput);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping(UrlConst.Address.getAddressId)
    public ResponseEntity<?> getAddressData(@PathVariable(UrlConst.id)Long id){
        AddressDataOutput addressDataOutput = addressService.viewDataAddress(id);
        return ResponseUtil.restSuccess(addressDataOutput);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping(UrlConst.Address.getAddressId)
    public ResponseEntity<?> deleteAddress(@PathVariable(UrlConst.id)Long id){
        AddressDataOutput addressDataOutput = addressService.deleteAddress(id);
        return ResponseUtil.restSuccess(addressDataOutput);
    }

}
