package com.ms.tourist_app.application.service.imp;

import com.ms.tourist_app.application.input.users.UserDataInput;
import com.ms.tourist_app.application.service.UserService;
import com.ms.tourist_app.config.exception.BadRequestException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImpIntegrationTest {

    @Autowired
    private UserService userService;

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