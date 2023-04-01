package com.ms.tourist_app.adapter.web.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NotBlank
@AllArgsConstructor
public class RestData<T> {
    private RestStatus restStatus;

    private String message;

    private List<String> reasons;

    private T data;


    /**
     * Dùng khi get data thành công, thì reasons sẽ trả về null**/
    public RestData(String message, T data) {
        this.restStatus = RestStatus.SUCCESS;
        this.message = message;
        this.data = data;
    }

    /**
     * Dùng khi get data thất bại, thì data sẽ null, reasons sẽ có**/
    public RestData( String message, List<String> reasons) {
        this.restStatus = RestStatus.ERROR;
        this.message = message;
        this.reasons = reasons;
    }

//    /**
//     * Dùng khi get data thất bại, thì data sẽ null, reasons sẽ có**/
//    public static RestData<?> error(String message,List<String> reasons){
//        return new RestData<>(RestStatus.ERROR,message,reasons);
//    }
}
