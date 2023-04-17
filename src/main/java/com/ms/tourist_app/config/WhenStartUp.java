package com.ms.tourist_app.config;

import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.application.dai.RoleRepository;
import com.ms.tourist_app.application.dai.UserRepository;
import com.ms.tourist_app.domain.entity.Role;
import com.ms.tourist_app.domain.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class WhenStartUp {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public WhenStartUp(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public void createRoleAndSetUser() {
        if (roleRepository.findByName("ROLE_ADMIN") == null) {

            roleRepository.save(new Role("ROLE_ADMIN"));
        }
        if (roleRepository.findByName("ROLE_USER") == null) {

            roleRepository.save(new Role("ROLE_USER"));
        }
        if (userRepository.findByEmail("admin@admin.admin") == null) {
            User user = new User();
            user.setEmail("admin@admin.admin");
            user.setPassword(passwordEncoder.encode("Abc123!@#"));
            Role role = roleRepository.findByName(AppStr.Role.adminRole);
            user.setRoles(List.of(role));
            userRepository.save(user);
            List<User> users = role.getUsers();
            users.add(user);
            role.setUsers(users);
            roleRepository.save(role);
        }
    }
}
