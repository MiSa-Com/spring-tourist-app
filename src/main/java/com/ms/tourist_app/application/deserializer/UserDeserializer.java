package com.ms.tourist_app.application.deserializer;

import com.ms.tourist_app.application.input.users.UserDataInput;

public interface UserDeserializer {
    UserDataInput deserialize(String json);
}
