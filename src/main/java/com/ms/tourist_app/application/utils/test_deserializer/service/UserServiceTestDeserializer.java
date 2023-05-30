package com.ms.tourist_app.application.utils.test_deserializer.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.tourist_app.application.utils.test_deserializer.TestDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceTestDeserializer implements TestDeserializer {
    private String pathFile;
    private String testCaseName;

    @Override
    public JsonNode retrieveTree() throws Exception {
        File file = new File(pathFile);
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser jsonParser = jsonFactory.createParser(file);
        jsonParser.setCodec(new ObjectMapper());
        JsonNode jsonNode = jsonParser.readValueAsTree();

        for (JsonNode node : jsonNode) {
            if (node.get("testCase").toString().equals("\"" + testCaseName + "\"")) {
                return node;
            }
        }
        return null;
    }

    @Override
    public <T> T deserialize(JsonNode jsonNode, String fieldName, Class<T> outputClass) {
        if (jsonNode == null) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String inputData = jsonNode.get(fieldName).toString();
            return objectMapper.readValue(inputData, outputClass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
