package com.ms.tourist_app.application.input.destinations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetListDestinationCenterRadiusInput {
    private Integer page;
    private Integer size;
    private String keyword;
    private Double radius; // in km
}
