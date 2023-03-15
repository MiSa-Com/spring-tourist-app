package com.ms.tourist_app.adapter.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestData<T> {
    private Integer httpStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Reason reason;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;



}
