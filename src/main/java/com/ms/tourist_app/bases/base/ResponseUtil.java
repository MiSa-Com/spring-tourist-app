package com.ms.tourist_app.bases.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public class ResponseUtil<T> {
    public ResponseEntity<?> resSuccess(T data) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new RestData<T>(HttpStatus.OK.value(), ResMessage.SUCCESS_MESSAGE, data)
        );
    }

    public ResponseEntity<?> resListSuccess(List<T> listData) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new RestData<>(HttpStatus.OK.value(), ResMessage.SUCCESS_MESSAGE, listData)
        );
    }

    public ResponseEntity<?> resSetSuccess(Set<T> data) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new RestData<>(HttpStatus.OK.value(), ResMessage.SUCCESS_MESSAGE, data)
        );
    }
}
