package com.ms.tourist_app.adapter.web.v1.transfer.parameter.destination_type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetListDestinationTypeParameter {
    private Integer page;
    private Integer size;
    private String keyword;
}
