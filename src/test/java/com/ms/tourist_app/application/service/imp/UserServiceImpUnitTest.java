package com.ms.tourist_app.application.service.imp;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.tourist_app.application.dai.AddressRepository;
import com.ms.tourist_app.application.dai.DestinationRepository;
import com.ms.tourist_app.application.dai.UserRepository;
import com.ms.tourist_app.application.utils.test_deserializer.TestDeserializer;
import com.ms.tourist_app.application.utils.test_deserializer.service.UserServiceTestDeserializer;
import com.ms.tourist_app.application.input.users.UserDataInput;
import com.ms.tourist_app.application.output.users.UserDataOutput;
import com.ms.tourist_app.application.service.UserService;
import com.ms.tourist_app.application.utils.JwtUtil;
import com.ms.tourist_app.config.exception.BadRequestException;
import com.ms.tourist_app.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@SpringBootTest
class UserServiceImpUnitTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    private UserServiceTestDeserializer userDeserializer = new UserServiceTestDeserializer();

    UserServiceImpUnitTest() {
        userDeserializer.setPathFile("src/main/resources/test_json/service/UserServiceTest.json");
    }

    Object deserializeInputData(JsonNode tree) throws Exception {
        JsonNode inputDataNode = tree.get("inputData");
        String inputClassName = inputDataNode.get("class").toString().replaceAll("\"", "");
        Class inputClass = Class.forName(inputClassName);
        Object objectInput = userDeserializer.deserialize(inputDataNode, "data", inputClass);
        return objectInput;
    }

    @TestConfiguration
    static class UserServiceImpTestContextConfiguration {
        @Bean
        public UserService userService(UserRepository userRepository, AddressRepository addressRepository,
                                       DestinationRepository destinationRepository, JwtUtil jwtUtil) {
            return new UserServiceImp(userRepository, addressRepository, destinationRepository, jwtUtil);
        }
    }

    @BeforeEach
    void setUp() {
        List<User> userList = userRepository.search("admin", PageRequest.of(0, 1));
        User admin = userList.get(0);
        admin.setTelephone("123456789");
        userRepository.save(admin);
    }

    @Test
    void createUser_Success() throws Exception {
        // given
        String testCaseName = "createUser_Success";
        userDeserializer.setTestCaseName(testCaseName);
        JsonNode jsonNode = userDeserializer.retrieveTree();

        // retrieve Nodes of test case
        Object objectInput = deserializeInputData(jsonNode);

        JsonNode expectedResultNode = jsonNode.get("expectedResult");
        String expectedData = expectedResultNode.get("data").toString().replaceAll("\"", "");
        LocalDate expected = LocalDate.parse(expectedData);

        // when
        UserDataOutput actual = userService.createUser((UserDataInput) objectInput);
        // then
        assertThat(actual.getDateOfBirth()).isEqualTo(expected);
    }

    @Test
    @Disabled
    void createUser_InvalidFormatDateOfBirth() {
        String dateOfBirth = "20-01-01";
        UserDataInput userDataInput = new UserDataInput("firstName", "lastName", dateOfBirth, 1L,
                "", "email@email", "password");
        assertThatThrownBy(() -> userService.createUser(userDataInput))
                .isInstanceOf(DateTimeParseException.class);
    }

    @Test
    @Disabled
    void createUser_InvalidFormatTelephone() {
        String telephone = "0123456789a";
        UserDataInput userDataInput = new UserDataInput("firstName", "lastName", "2020-01-01", 1L,
                telephone, "email", "password");
        assertThatThrownBy(() -> userService.createUser(userDataInput))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Telephone is invalid");
    }

    @Test
    void createUser_duplicateTelephone() throws Exception {
        // given
        String testCaseName = "createUser_duplicateTelephone";
        userDeserializer.setTestCaseName(testCaseName);
        JsonNode jsonNode = userDeserializer.retrieveTree();

        // retrieve Nodes of test case
        Object objectInput = deserializeInputData(jsonNode);

        JsonNode expectedResultNode = jsonNode.get("expectedResult");
        String expectedClassName = expectedResultNode.get("class").toString().replaceAll("\"", "");
        Class expectedException = Class.forName(expectedClassName);
        String expectedMessage = expectedResultNode.get("message").toString().replaceAll("\"", "");
        // when
        UserDataInput userDataInput = (UserDataInput) objectInput;
        assertThatThrownBy(() -> userService.createUser(userDataInput))
                .isInstanceOf(expectedException)
                .hasMessage(expectedMessage);
    }

    @Test
    void createUser_duplicateEmail() throws Exception {
        // given
        String testCaseName = "createUser_duplicateEmail";
        userDeserializer.setTestCaseName(testCaseName);
        JsonNode jsonNode = userDeserializer.retrieveTree();

        // retrieve Nodes of test case
        Object objectInput = deserializeInputData(jsonNode);

        JsonNode expectedResultNode = jsonNode.get("expectedResult");
        String expectedClassName = expectedResultNode.get("class").toString().replaceAll("\"", "");
        Class expectedException = Class.forName(expectedClassName);
        String expectedMessage = expectedResultNode.get("message").toString().replaceAll("\"", "");
        // when
        UserDataInput userDataInput = (UserDataInput) objectInput;
        assertThatThrownBy(() -> userService.createUser(userDataInput))
                .isInstanceOf(expectedException)
                .hasMessage(expectedMessage);
    }

    @Test
    @Disabled
    void createUser_invalidEmail() {
        String email = "admin";
        UserDataInput userDataInput = new UserDataInput("firstName", "lastName", "2020-01-01", 1L,
                "0123456789", email, "password");
        assertThatThrownBy(() -> userService.createUser(userDataInput))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Email is invalid");
    }
}