package com.ms.tourist_app.application.input.provinces;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetListProvinceDataInput {
    private String keyword;
    private Integer page;
    private Integer size;
}
