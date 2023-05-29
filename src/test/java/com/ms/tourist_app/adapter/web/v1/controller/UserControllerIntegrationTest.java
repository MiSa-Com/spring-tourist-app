package com.ms.tourist_app.adapter.web.v1.controller;

import com.ms.tourist_app.application.dai.UserRepository;
import com.ms.tourist_app.application.input.users.GetListUserInput;
import com.ms.tourist_app.application.mapper.UserMapper;
import com.ms.tourist_app.application.output.users.UserDataOutput;
import com.ms.tourist_app.application.service.UserService;
import com.ms.tourist_app.application.utils.JwtUtil;
import com.ms.tourist_app.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtUtil jwtUtil;

    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void getAllUser() throws Exception {
        List<User> listAllUsers = userRepository.findAll();
        List<UserDataOutput> listAllUsersOutput = new ArrayList<>();
        for (User user : listAllUsers) {
            UserDataOutput userDataOutput = userMapper.toUserDataOutput(user);
            listAllUsersOutput.add(userDataOutput);
        }
        GetListUserInput getListUserInput = new GetListUserInput(0, 100, "");

        given(userService.getListUserOutPut(getListUserInput)).willReturn(listAllUsersOutput);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}