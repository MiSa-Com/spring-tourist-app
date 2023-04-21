package com.ms.tourist_app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.model.LatLng;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.application.dai.ProvinceRepository;
import com.ms.tourist_app.application.dai.RoleRepository;
import com.ms.tourist_app.application.dai.UserRepository;
import com.ms.tourist_app.application.utils.GoogleMapApi;
import com.ms.tourist_app.domain.dto.ProvinceDTO;
import com.ms.tourist_app.domain.entity.Province;
import com.ms.tourist_app.domain.entity.Role;
import com.ms.tourist_app.domain.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Configuration
public class WhenStartUp {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ProvinceRepository provinceRepository;
    private final PasswordEncoder passwordEncoder;

    public WhenStartUp(RoleRepository roleRepository, UserRepository userRepository, ProvinceRepository provinceRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.provinceRepository = provinceRepository;
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

    @Bean
    public void readData() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getResourceAsStream("/province.json")) {
            ProvinceDTO[] provinceDTOS = mapper.readValue(inputStream, ProvinceDTO[].class);
            List<ProvinceDTO> provinceDTOList = Arrays.asList(provinceDTOS);
            List<Province> provinces = provinceRepository.findAll();
            if (provinces.isEmpty()) {
                for (ProvinceDTO provinceDTO :
                        provinceDTOList) {
                    Province province = new Province();
                    province.setName(provinceDTO.getName());
                    province.setDivisionType(provinceDTO.getDivisionType());
                    province.setLongitude(provinceDTO.getLongitude());
                    province.setLatitude(provinceDTO.getLatitude());

                    provinceRepository.save(province);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
