package com.ms.tourist_app.application.service.imp;

import com.fasterxml.jackson.databind.JsonNode;
import com.ms.tourist_app.application.dai.*;
import com.ms.tourist_app.application.input.users.UserDataInput;
import com.ms.tourist_app.application.output.users.UserDataOutput;
import com.ms.tourist_app.application.service.UserService;
import com.ms.tourist_app.application.utils.JwtUtil;
import com.ms.tourist_app.application.utils.test_deserializer.TestDeserializer;
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

@SpringBootTest
class UserServiceImpUnitTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    private TestDeserializer userTestDeserializer = new TestDeserializer();

    UserServiceImpUnitTest() {
        userTestDeserializer.setPathFile("src/main/resources/test_json/service/UserServiceTest.json");
    }

    @TestConfiguration
    static class UserServiceImpTestContextConfiguration {
        @Bean
        public UserService userService(UserRepository userRepository, AddressRepository addressRepository,
                                       DestinationRepository destinationRepository,
                                       ImageDestinationRepository imageDestinationRepository, JwtUtil jwtUtil) {
            return new UserServiceImp(userRepository, addressRepository, destinationRepository, imageDestinationRepository, jwtUtil);
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
        userTestDeserializer.setTestCaseName(testCaseName);
        JsonNode jsonNode = userTestDeserializer.retrieveTree();

        // retrieve Nodes of test case
        Object objectInput = userTestDeserializer.deserializeInputData(jsonNode);

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
        userTestDeserializer.setTestCaseName(testCaseName);
        JsonNode jsonNode = userTestDeserializer.retrieveTree();

        // retrieve Nodes of test case
        Object objectInput = userTestDeserializer.deserializeInputData(jsonNode);

        Class expectedException = userTestDeserializer.deserializeException(jsonNode);
        String expectedMessage = userTestDeserializer.getMessageException(jsonNode);
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
        userTestDeserializer.setTestCaseName(testCaseName);
        JsonNode jsonNode = userTestDeserializer.retrieveTree();

        // retrieve Nodes of test case
        Object objectInput = userTestDeserializer.deserializeInputData(jsonNode);

        Class expectedException = userTestDeserializer.deserializeException(jsonNode);
        String expectedMessage = userTestDeserializer.getMessageException(jsonNode);
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