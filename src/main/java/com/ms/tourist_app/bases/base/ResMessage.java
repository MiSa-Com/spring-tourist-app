package com.ms.tourist_app.bases.base;

public enum ResMessage {
    SUCCESS_MESSAGE("Response success"), ERROR_MESSAGE("Response error");
    private String message;

    ResMessage(String message) {
        this.message = message;
    }
}
