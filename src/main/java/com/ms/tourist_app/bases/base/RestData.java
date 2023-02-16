package com.ms.tourist_app.bases.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestData<T> {
    private Integer httpStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ResMessage userMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String devMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public RestData(Integer httpStatus, ResMessage userMessage, T data) {
        this.httpStatus = httpStatus;
        this.userMessage = userMessage;
        this.data = data;
    }

}
