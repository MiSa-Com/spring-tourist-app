package com.ms.tourist_app.application.service.imp;

import com.ms.tourist_app.application.dai.AddressRepository;
import com.ms.tourist_app.application.dai.DestinationRepository;
import com.ms.tourist_app.application.dai.UserRepository;
import com.ms.tourist_app.application.input.users.UserDataInput;
import com.ms.tourist_app.application.service.UserService;
import com.ms.tourist_app.application.utils.JwtUtil;
import com.ms.tourist_app.config.exception.BadRequestException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImpIntegrationTest {
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
    public void createUser_duplicateEmail() {
        String email = "admin@admin.admin";
        UserDataInput userDataInput = new UserDataInput("firstName", "lastName", "dateOfBirth", 1L,
                "telephone", email, "password");
        try {
            userService.createUser(userDataInput);
            assert false;
        } catch (BadRequestException e) {
            String message = e.getMessage();
            assert message.equals("email Duplicate");
        }

    }
}