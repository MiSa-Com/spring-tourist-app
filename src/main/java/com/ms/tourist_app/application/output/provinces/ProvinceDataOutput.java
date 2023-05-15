package com.ms.tourist_app.application.output.provinces;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceDataOutput {
    private Long id;
    private String name;
    private String nameShort;
    private String divisionType;
    private Double longitude;
    private Double latitude;
}
