package com.ms.tourist_app.application.annotation.validator;

import com.ms.tourist_app.application.annotation.CheckUser;
import com.ms.tourist_app.application.dai.UserRepository;
import com.ms.tourist_app.application.input.users.UserDataInput;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class CheckUserValidator implements ConstraintValidator<CheckUser, UserDataInput>  {


    @Override
    public void initialize(CheckUser constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    private final UserRepository userRepository;

    public CheckUserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private Boolean checkEmail(String email){
        return userRepository.findByEmail(email)!= null;
    }
    private Boolean checkTelephone(String telephone){
        return userRepository.findByTelephone(telephone)!= null;
    }

    @Override
    public boolean isValid(UserDataInput input, ConstraintValidatorContext constraintValidatorContext) {
        List<String> message = new ArrayList<>();
        if (checkEmail(input.getEmail())) {
            message.add("Trung email");
        }
        if (checkTelephone(input.getTelephone())) {
            message.add("Trùng telephone");
        }



        //logic here
        return false;
    }
}
