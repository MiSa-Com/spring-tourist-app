package com.ms.tourist_app.domain.dto;

import com.ms.tourist_app.application.constants.AppStr;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank(message = AppStr.Validation.mustNotNull)
    private String firstName;


    @NotBlank(message = AppStr.Validation.mustNotNull)
    private String lastName;


    @NotBlank(message = AppStr.Validation.mustNotNull)
    private String dateOfBirth;


    private String address;


    @NotBlank(message = AppStr.Validation.mustNotNull)
    private String telephone;


    @NotBlank(message = AppStr.Validation.mustNotNull)
    @Email(message = AppStr.Validation.incorrectFormat)
    private String email;


    @NotBlank(message = AppStr.Validation.mustNotNull)
    private String password;


    public UserDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
