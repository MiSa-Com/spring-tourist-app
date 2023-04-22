package com.ms.tourist_app.application.input.provinces;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetProvinceByCoordinateDataInput {
    private Double lon;
    private Double lat;
}
