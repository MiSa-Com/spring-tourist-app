package com.ms.tourist_app.application.deserializer.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.tourist_app.application.deserializer.UserDeserializer;
import com.ms.tourist_app.application.input.users.UserDataInput;

public class UserDataInputDeserializer implements UserDeserializer {
    @Override
    public UserDataInput deserialize(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, UserDataInput.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
