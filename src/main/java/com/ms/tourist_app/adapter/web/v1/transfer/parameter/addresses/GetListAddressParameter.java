package com.ms.tourist_app.adapter.web.v1.transfer.parameter.addresses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetListAddressParameter {
    private Integer page;
    private Integer size;
    private Long idProvince;
    private String keyword;
}
