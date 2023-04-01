package com.ms.tourist_app.application.input.type_destination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetListDestinationTypeInput {

    private Integer page;
    private Integer size;
    private String keyword;

}
