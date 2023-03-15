package com.ms.tourist_app.application.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ms.tourist_app.adapter.base.Reason;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse<T> {

    private Integer httpStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Reason reason;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

}
