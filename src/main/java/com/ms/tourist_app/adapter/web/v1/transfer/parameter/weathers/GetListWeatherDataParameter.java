package com.ms.tourist_app.adapter.web.v1.transfer.parameter.weathers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetListWeatherDataParameter {
    private String keyword;
    private Integer page;
    private Integer size;
}
