package com.ms.tourist_app.application.service.imp;

import com.ms.tourist_app.application.dai.AddressRepository;
import com.ms.tourist_app.application.dai.DestinationRepository;
import com.ms.tourist_app.application.dai.ProvinceRepository;
import com.ms.tourist_app.application.dai.UserRepository;
import com.ms.tourist_app.application.input.users.GetListUserInput;
import com.ms.tourist_app.application.input.users.UserDataInput;
import com.ms.tourist_app.application.mapper.UserMapper;
import com.ms.tourist_app.application.output.users.UserDataOutput;
import com.ms.tourist_app.application.service.UserService;
import com.ms.tourist_app.application.utils.JwtUtil;
import com.ms.tourist_app.config.exception.BadRequestException;
import com.ms.tourist_app.domain.entity.Address;
import com.ms.tourist_app.domain.entity.Province;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.time.format.DateTimeParseException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

class UserServiceImpUnitTest {
    private UserService userService;
    private AutoCloseable autoCloseable;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private DestinationRepository destinationRepository;
    @Mock
    private JwtUtil jwtUtil;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

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
        autoCloseable = MockitoAnnotations.openMocks(this);
        userService = new UserServiceImp(userRepository, addressRepository,
                destinationRepository, jwtUtil);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllUser() {
        // given
        int page = 0;
        int size = 10;
        String keyword = "";
        GetListUserInput getListUserInput = new GetListUserInput(page, size, keyword);
        // when
        userService.getListUserOutPut(getListUserInput);
        // then
        verify(userRepository).findAll();
    }

    @Test
    void createUser_Success() {
        // given
        UserDataInput userDataInput = new UserDataInput("firstName", "lastName", "2020-01-01", 1L,
                "0123", "abc@gmail.com", "password");

        // when
        UserDataOutput output = userService.createUser(userDataInput);

        // then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());

        User user = userArgumentCaptor.getValue();
        UserDataOutput expectedOutput = userMapper.toUserDataOutput(user);

        assertThat(output).isEqualToComparingFieldByField(expectedOutput);
    }

    @Test
    void createUser_InvalidFormatDateOfBirth() {
        String dateOfBirth = "20-01-01";
        UserDataInput userDataInput = new UserDataInput("firstName", "lastName", dateOfBirth, 1L,
                "telephone", "email", "password");
        assertThatThrownBy(() -> userService.createUser(userDataInput))
                .isInstanceOf(DateTimeParseException.class);
    }

    @Test
    void createUser_InvalidFormatTelephone() {
        // given
        String telephone = "0123456789a";
        UserDataInput userDataInput = new UserDataInput("firstName", "lastName", "2020-01-01", 1L,
                telephone, "email", "password");
        // when
        userService.createUser(userDataInput);
        // then

        assertThatThrownBy(() -> userService.createUser(userDataInput))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("telephone Invalid");
    }

    @Test
    void createUser_duplicateTelephone() {
        String telephone = "012345";
        UserDataInput userDataInput = new UserDataInput("firstName", "lastName", "2020-01-01", 1L,
                telephone, "email", "password");
        assertThatThrownBy(() -> userService.createUser(userDataInput))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("telephone Duplicate");
    }

    @Test
    void createUser_duplicateEmail() {
        String email = "admin@admin.admin";
        UserDataInput userDataInput = new UserDataInput("firstName", "lastName", "2020-01-01", 1L,
                "01234567", email, "password");
        assertThatThrownBy(() -> userService.createUser(userDataInput))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("email Duplicate");
    }

    @Test
    void createUser_invalidEmail() {
        String email = "admin";
        UserDataInput userDataInput = new UserDataInput("firstName", "lastName", "2020-01-01", 1L,
                "0123456789", email, "password");
        assertThatThrownBy(() -> userService.createUser(userDataInput))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("email Invalid");
    }
}