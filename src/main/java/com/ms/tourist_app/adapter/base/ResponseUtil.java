package com.ms.tourist_app.adapter.base;

import com.ms.tourist_app.application.constants.StringBase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public class ResponseUtil<T> {
    public ResponseEntity<?> resSuccess(T data) {
        Reason reason = new Reason();
        return ResponseEntity.status(HttpStatus.OK).body(
                new RestData<T>(HttpStatus.OK.value(), StringBase.success, reason, data)
        );
    }

    public ResponseEntity<?> resListSuccess(List<T> listData) {
        Reason reason = new Reason();
        return ResponseEntity.status(HttpStatus.OK).body(
                new RestData<>(HttpStatus.OK.value(), StringBase.success, reason, listData)
        );
    }

    public ResponseEntity<?> resSetSuccess(Set<T> data) {
        Reason reason = new Reason();
        return ResponseEntity.status(HttpStatus.OK).body(
                new RestData(HttpStatus.OK.value(), StringBase.success, reason, data)
        );
    }
}
