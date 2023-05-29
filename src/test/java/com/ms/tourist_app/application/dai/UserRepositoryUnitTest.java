package com.ms.tourist_app.application.dai;

import com.ms.tourist_app.domain.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryUnitTest {
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void checkIfAdminExistsEmail() {
        // given
        String email = "admin@admin.admin";
        String password = "123";
        int nbPage = 0;
        int nbElement = 1;
        User user = new User(email, password, List.of());
        userRepository.save(user);
        // when
        List<User> listUsers = userRepository.search(email, PageRequest.of(nbPage, nbElement));
        User expectedUser;

        expectedUser = listUsers.get(0);
        // then
        assert(user.getEmail().equals(expectedUser.getEmail()));
    }

    @Test
    void checkIfAdminDoesNotExist() {
        // given
        String email = "admin@admin.admin";
        // when
        List<User> listUsers = userRepository.search(email, PageRequest.of(0, 1));
        // then
        assert (listUsers.isEmpty());
    }
}