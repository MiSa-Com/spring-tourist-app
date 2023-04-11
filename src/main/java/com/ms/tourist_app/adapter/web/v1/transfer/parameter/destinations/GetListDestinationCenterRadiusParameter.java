package com.ms.tourist_app.adapter.web.v1.transfer.parameter.destinations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetListDestinationCenterRadiusParameter {
    private Integer page;
    private Integer size;
    private String keyword;
    private Double radius; // in km
    @Nullable
    private int maxResult;
}
