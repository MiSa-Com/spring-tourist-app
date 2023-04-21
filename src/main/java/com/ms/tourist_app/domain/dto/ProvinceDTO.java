package com.ms.tourist_app.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceDTO {

    private String name;

    private String divisionType;

    private Double longitude;

    private Double latitude;


}
