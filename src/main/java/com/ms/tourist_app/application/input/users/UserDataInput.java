package com.ms.tourist_app.application.input.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDataInput {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private Long idAddress;
    private String telephone;
    private String email;
    private String password;
}
