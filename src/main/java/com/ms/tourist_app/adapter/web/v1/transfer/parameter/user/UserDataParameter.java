package com.ms.tourist_app.adapter.web.v1.transfer.parameter.user;

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
public class UserDataParameter {

    @NotBlank(message = AppStr.Validation.mustNotNull)
    private String firstName;


    @NotBlank(message = AppStr.Validation.mustNotNull)
    private String lastName;


    @NotBlank(message = AppStr.Validation.mustNotNull)
    private String dateOfBirth;


    private Long idAddress;


    @NotBlank(message = AppStr.Validation.mustNotNull)
    private String telephone;


    @NotBlank(message = AppStr.Validation.mustNotNull)
    @Email(message = AppStr.Validation.incorrectFormat)
    private String email;


    @NotBlank(message = AppStr.Validation.mustNotNull)
    private String password;
}
