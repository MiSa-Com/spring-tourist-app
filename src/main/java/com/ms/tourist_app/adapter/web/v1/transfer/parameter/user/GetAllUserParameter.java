package com.ms.tourist_app.adapter.web.v1.transfer.parameter.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllUserParameter {
    private Integer page;
    private Integer size;
    private String keyword;
}
