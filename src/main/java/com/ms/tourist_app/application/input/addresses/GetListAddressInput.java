package com.ms.tourist_app.application.input.addresses;

import com.ms.tourist_app.domain.entity.Province;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetListAddressInput {
    private Integer page;
    private Integer size;
    private Long idProvince;
    private String keyword;
}
