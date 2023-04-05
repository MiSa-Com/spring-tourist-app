package com.ms.tourist_app.application.input.addresses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDataInput {
    private Long idProvince;
    private String detailAddress;

}
