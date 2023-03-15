package com.ms.tourist_app.adapter.base;

public enum ResponseMessage{
    SUCCESS("Response success"), ERROR("Response error");
    private String message;

    ResponseMessage(String message) {
        this.message = message;
    }
}
