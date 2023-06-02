package com.ms.tourist_app.application.utils.test_deserializer;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestDeserializer {
    private String pathFile;
    private String testCaseName;
    private <T> T deserialize(JsonNode jsonNode, String fieldName, Class<T> outputClass) {
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

    public Object deserializeInputData(JsonNode tree) throws Exception {
        JsonNode inputDataNode = tree.get("inputData");
        String inputClassName = inputDataNode.get("class").toString().replaceAll("\"", "");
        Class inputClass = Class.forName(inputClassName);
        Object objectInput = this.deserialize(inputDataNode, "data", inputClass);
        return objectInput;
    }

    public Class deserializeException(JsonNode tree) throws Exception {
        JsonNode exceptionNode = tree.get("expectedResult");
        String exceptionClassName = exceptionNode.get("class").toString().replaceAll("\"", "");
        Class exceptionClass = Class.forName(exceptionClassName);
        return exceptionClass;
    }

    public String getMessageException(JsonNode tree) {
        JsonNode exceptionNode = tree.get("expectedResult");
        String message = exceptionNode.get("message").toString().replaceAll("\"", "");
        return message;
    }
}
