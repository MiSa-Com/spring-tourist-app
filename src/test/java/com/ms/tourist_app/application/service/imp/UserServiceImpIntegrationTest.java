package com.ms.tourist_app.application.service.imp;

import com.ms.tourist_app.application.dai.AddressRepository;
import com.ms.tourist_app.application.dai.DestinationRepository;
import com.ms.tourist_app.application.dai.UserRepository;
import com.ms.tourist_app.application.input.users.UserDataInput;
import com.ms.tourist_app.application.output.users.UserDataOutput;
import com.ms.tourist_app.application.service.UserService;
import com.ms.tourist_app.application.utils.JwtUtil;
import com.ms.tourist_app.config.exception.BadRequestException;
import org.junit.Test;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImpIntegrationTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

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
    public void createUser_ValidFormatDateOfBirth() {
        // Given
        String dateOfBirth = "2020-01-01";
        UserDataInput userDataInput = new UserDataInput("firstName", "lastName", dateOfBirth, 1L,
                "telephone", "abc@gmail.com", "password");

        // When
        UserDataOutput output = userService.createUser(userDataInput);

        // Then
        LocalDate expected = LocalDate.parse(dateOfBirth);
        assertThat(output.getDateOfBirth()).isEqualTo(expected);
    }

    @Test(expected = java.time.format.DateTimeParseException.class)
    public void createUser_InvalidFormatDateOfBirth() {
        String dateOfBirth = "20-01-01";
        UserDataInput userDataInput = new UserDataInput("firstName", "lastName", dateOfBirth, 1L,
                "telephone", "email", "password");
        userService.createUser(userDataInput);
    }

    @Test
    public void createUser_InvalidFormatTelephone() {
        String telephone = "0123456789a";
        UserDataInput userDataInput = new UserDataInput("firstName", "lastName", "2020-01-01", 1L,
                telephone, "email", "password");
        exceptionRule.expect(BadRequestException.class);
        exceptionRule.expectMessage("Telephone is invalid");
        userService.createUser(userDataInput);
    }

    @Test
    public void createUser_duplicateTelephone() {
        String telephone = "012345";
        UserDataInput userDataInput = new UserDataInput("firstName", "lastName", "2020-01-01", 1L,
                telephone, "email", "password");
        exceptionRule.expect(BadRequestException.class);
        exceptionRule.expectMessage("telephone Duplicate");
        userService.createUser(userDataInput);
    }

    @Test(expected = BadRequestException.class)
    public void createUser_duplicateEmail() {
        String email = "admin@admin.admin";
        UserDataInput userDataInput = new UserDataInput("firstName", "lastName", "dateOfBirth", 1L,
                "0123456789", email, "password");
        userService.createUser(userDataInput);
    }

    @Test
    public void createUser_invalidEmail() {
        String email = "admin";
        UserDataInput userDataInput = new UserDataInput("firstName", "lastName", "2020-01-01", 1L,
                "0123456789", email, "password");
        exceptionRule.expect(BadRequestException.class);
        exceptionRule.expectMessage("Email is invalid");
        userService.createUser(userDataInput);
    }
}