package com.ms.tourist_app.adapter.web.base;

import com.ms.tourist_app.application.constants.AppStr;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseUtil {
    /**
     * Sử dụng khi lấy data thành công
     * **/
    public static ResponseEntity<?> restSuccess(Object data){
        RestData<?> restData = new RestData<>(AppStr.Response.getDataSuccess,data);
        return ResponseEntity.ok(restData);
    }

    /**
     * Sử dụng khi lấy data không thành công
     * **/
    public static ResponseEntity<RestData<?>> error(HttpStatus status,List<String> message){
        RestData<?> restData = new RestData<>(AppStr.Response.getDataError,message);
        return new ResponseEntity<>(restData,status);
    }
}
