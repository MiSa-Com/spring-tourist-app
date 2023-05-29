package com.ms.tourist_app.application.service.imp;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.tourist_app.application.dai.AddressRepository;
import com.ms.tourist_app.application.dai.DestinationRepository;
import com.ms.tourist_app.application.dai.UserRepository;
import com.ms.tourist_app.application.deserializer.UserDeserializer;
import com.ms.tourist_app.application.deserializer.input.UserDataInputDeserializer;
import com.ms.tourist_app.application.input.users.GetListUserInput;
import com.ms.tourist_app.application.input.users.UserDataInput;
import com.ms.tourist_app.application.mapper.UserMapper;
import com.ms.tourist_app.application.output.users.UserDataOutput;
import com.ms.tourist_app.application.service.UserService;
import com.ms.tourist_app.application.utils.JwtUtil;
import com.ms.tourist_app.config.exception.BadRequestException;
import com.ms.tourist_app.domain.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@SpringBootTest
class UserServiceImpUnitTest {

    @Autowired
    private UserService userService;

    @TestConfiguration
    static class UserServiceImpTestContextConfiguration {
        @Bean
        public UserService userService(UserRepository userRepository, AddressRepository addressRepository,
                                       DestinationRepository destinationRepository, JwtUtil jwtUtil) {
            return new UserServiceImp(userRepository, addressRepository, destinationRepository, jwtUtil);
        }
    }

    @Test
    void createUser_Success() throws Exception {
        // read json file
        File file = new File("src/main/resources/test_json/service/UserServiceTest.json");
        JsonFactory jsonFactory = new JsonFactory();
        JsonParser jsonParser = jsonFactory.createParser(file);
        jsonParser.setCodec(new ObjectMapper());
        JsonNode jsonNode = jsonParser.readValueAsTree();

        String inputDataJson = jsonNode.get(0).get("inputData").toString();
        String expectedResultJson = jsonNode.get(0).get("expectedResult").toString();

        // deserialize json to UserDataInput
        UserDeserializer userDeserializer = new UserDataInputDeserializer();
        UserDataInput userDataInput = userDeserializer.deserialize(inputDataJson);

        // when
        UserDataOutput actualResult = userService.createUser(userDataInput);
        // then
        String expectedResult = expectedResultJson.replaceAll("\"", "");
        LocalDate expected = LocalDate.parse(expectedResult);
        assertThat(actualResult.getDateOfBirth()).isEqualTo(expected);
    }

    @Test
    void createUser_InvalidFormatDateOfBirth() {
        String dateOfBirth = "20-01-01";
        UserDataInput userDataInput = new UserDataInput("firstName", "lastName", dateOfBirth, 1L,
                "0000000", "email", "password");
        assertThatThrownBy(() -> userService.createUser(userDataInput))
                .isInstanceOf(DateTimeParseException.class);
    }

    @Test
    void createUser_InvalidFormatTelephone() {
        String telephone = "0123456789a";
        UserDataInput userDataInput = new UserDataInput("firstName", "lastName", "2020-01-01", 1L,
                telephone, "email", "password");
        assertThatThrownBy(() -> userService.createUser(userDataInput))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Telephone is invalid");
    }

    @Test
    void createUser_duplicateTelephone() {
        String telephone = "012345";
        UserDataInput userDataInput = new UserDataInput("firstName", "lastName", "2020-01-01", 1L,
                telephone, "email", "password");
        assertThatThrownBy(() -> userService.createUser(userDataInput))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("telephone Duplicate");
    }

    @Test
    void createUser_duplicateEmail() {
        String email = "admin@admin.admin";
        UserDataInput userDataInput = new UserDataInput("firstName", "lastName", "dateOfBirth", 1L,
                "012345678", email, "password");
        assertThatThrownBy(() -> userService.createUser(userDataInput))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("email Duplicate");
    }

    @Test
    void createUser_invalidEmail() {
        String email = "admin";
        UserDataInput userDataInput = new UserDataInput("firstName", "lastName", "2020-01-01", 1L,
                "0123456789", email, "password");
        assertThatThrownBy(() -> userService.createUser(userDataInput))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Email is invalid");
    }
}