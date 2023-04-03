package com.ms.tourist_app.application.input.hotels;

import com.ms.tourist_app.domain.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetListHotelDataInput {
    private String keyword;
    private Integer page;
    private Integer size;
}
