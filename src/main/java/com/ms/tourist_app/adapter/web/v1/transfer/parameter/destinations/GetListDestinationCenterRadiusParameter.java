package com.ms.tourist_app.adapter.web.v1.transfer.parameter.destinations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetListDestinationCenterRadiusParameter {
    private Integer page;
    private Integer size;

    @NotBlank(message = "Destination must not blank")
    private String keyword;
    private Double radius; // in km
}
