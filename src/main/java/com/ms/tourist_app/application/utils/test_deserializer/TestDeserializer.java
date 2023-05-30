package com.ms.tourist_app.application.utils.test_deserializer;

import com.fasterxml.jackson.databind.JsonNode;

public interface TestDeserializer {
    JsonNode retrieveTree() throws Exception;
    <T> T deserialize(JsonNode jsonNode, String fieldName, Class<T> outputClass);
}
