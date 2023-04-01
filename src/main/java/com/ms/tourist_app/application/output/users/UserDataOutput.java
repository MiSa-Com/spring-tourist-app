package com.ms.tourist_app.application.output.users;

import com.ms.tourist_app.domain.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDataOutput {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Address address;
    private String telephone;
    private String email;
    private String password;

}

